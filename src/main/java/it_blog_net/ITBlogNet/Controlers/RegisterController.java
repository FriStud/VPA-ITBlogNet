package it_blog_net.ITBlogNet.Controlers;

import it_blog_net.ITBlogNet.Formulas.RegisterFormula;
import it_blog_net.ITBlogNet.Models.User;
import it_blog_net.ITBlogNet.Services.NotificationService;
import it_blog_net.ITBlogNet.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/users/userlist")
    public String getUserList(ModelMap modelMap)
    {
        modelMap.addAttribute("allUsers",userService.findAll());
        return "/users/userlist";
    }

    @RequestMapping("/users/register")
    public String register(RegisterFormula registerFormula)
    {
        return "/users/register";
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public String registerPage(RegisterFormula registerFormula, BindingResult bindingResult) throws FileNotFoundException, UnsupportedEncodingException {
        if(bindingResult.hasErrors())
        {
            notificationService.addErrorMessage("Registring formula filled up uncorectly!");
            return "/users/register";
        }

        if(!userService.register(registerFormula.getUserName(),registerFormula.getPass(), registerFormula.getRePass(),registerFormula.getEmail()))
        {
            notificationService.addErrorMessage("Invalid validation of registring!");
            return "/users/register";
        }

        if(userService.isRegisterEmail(registerFormula.getEmail()))
        {
            notificationService.addErrorMessage("This email is allready being used by another user!");
            return "/users/register";
        }

            PrintWriter pri = new PrintWriter("myfile.txt", "UTF-8");
            pri.write(registerFormula.getUserName());
            pri.write(registerFormula.getEmail());
            pri.close();

        userService.create(new User(registerFormula.getUserName(),registerFormula.getEmail(),passwordEncoder.encode(registerFormula.getPass())));

        notificationService.addInfoMessage("You've been sucessfully registered. Now you can Long in.");

        return "redirect:/users/login";
    }

}
