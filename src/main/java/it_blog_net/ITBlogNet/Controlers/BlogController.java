package it_blog_net.ITBlogNet.Controlers;

import it_blog_net.ITBlogNet.Formulas.PostFormula;
import it_blog_net.ITBlogNet.Models.Post;
import it_blog_net.ITBlogNet.Models.User;
import it_blog_net.ITBlogNet.Services.NotificationService;
import it_blog_net.ITBlogNet.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BlogController {



    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PostService postService;

    @GetMapping("/blog/blog")
    public String blog(ModelMap model, HttpSession session)
    {
        User user = (User) session.getAttribute("loggedUser");
        if(user == null)
        {
            notificationService.addErrorMessage("You have to be Logged in to create or edit your own post!");
            return "redirect:/";
        }

        model.addAttribute("posts", postService.getPostWithTeasser(user.getId()));

        return "/blog/blog";
    }

}
