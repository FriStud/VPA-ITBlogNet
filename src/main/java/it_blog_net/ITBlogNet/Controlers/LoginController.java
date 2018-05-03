package it_blog_net.ITBlogNet.Controlers;

import it_blog_net.ITBlogNet.Formulas.LoginFormula;
import it_blog_net.ITBlogNet.Models.User;
import it_blog_net.ITBlogNet.Services.NotificationService;
import it_blog_net.ITBlogNet.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/users/login")
    public String login(ModelMap model,HttpSession session, HttpServletRequest request)
    {
        model.addAttribute("login", new LoginFormula());
        User loggeduser = (User)session.getAttribute("loggedUser");
        User user = checkCookies(request);
        if(user == null && loggeduser == null)
            return "/users/login";
        else
        {
            if(loggeduser != null)
            {
                notificationService.addInfoMessage(String.format("You are already logged in as \"%s\" if you want to change account logout before", loggeduser.getUsername()));
                return "redirect:/";
            }
            else if( userService.authenticateUser(user.getUsername(),user.getPasswordHash()))
            {
                user = userService.findByName(user.getUsername());
                session.setAttribute("userName", user.getUsername());
                session.setAttribute("loggedUser", user);
                notificationService.addInfoMessage(String.format("You are already logged in as %s if you want to change account logout before", user.getUsername()));
                return "redirect:/";
            }
            else
            {
                notificationService.addErrorMessage("Automatic Authentication Failed!");
                return "/users/login";
            }
        }
    }

    @PostMapping("/users/login")
    public String loginPage(@ModelAttribute(value = "login") LoginFormula login, BindingResult bindingResult, ModelMap model,
                            HttpSession httpSession, HttpServletRequest request, HttpServletResponse response
                            ) throws FileNotFoundException, UnsupportedEncodingException
    {
        if(bindingResult.hasErrors())
        {
            notificationService.addErrorMessage("Fill up form correctly!");
            return "/users/login";
        }

        if(!userService.authenticateUser(login.getUserName(),login.getPass()))
        {
            notificationService.addErrorMessage("Invalid user's authentication!");
            return "/users/login";
        }

        User user = userService.findByName(login.getUserName());

        if(login.isRememberMe())
        {
            Cookie cName = new Cookie("username",login.getUserName());
            cName.setMaxAge(3600);
            response.addCookie(cName);
            Cookie cPass = new Cookie("passwordHash", login.getPass());
            cPass.setMaxAge(3600);
            response.addCookie(cPass);
        }

        httpSession.setAttribute("userName", user.getUsername());
        httpSession.setAttribute("loggedUser",user);
        notificationService.addInfoMessage("Login successfully!");
        return "redirect:/";
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, HttpServletRequest request,HttpServletResponse response)
    {
        session.setAttribute("loggedUser", null);
        for (Cookie cookie: request.getCookies())
        {
            if (cookie.getName().equalsIgnoreCase("username"))
            {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

            if (cookie.getName().equalsIgnoreCase("passwordHash"))
            {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        notificationService.addInfoMessage("You Have Been Successfully Logged Out");
        return "redirect:/users/login";
    }


    protected User checkCookies(HttpServletRequest request)
    {
        Cookie[]cookies = request.getCookies();
        User user = null;
        String userName = "", password = "";

        for (Cookie cookie : cookies)
        {
            if(cookie.getName().equalsIgnoreCase("username"))
                userName = cookie.getValue();
            if(cookie.getName().equalsIgnoreCase("passwordHash"))
                password = cookie.getValue();
        }

        if(!userName.isEmpty() && !password.isEmpty())
            user = new User(userName,password);

        return user;
    }
}
