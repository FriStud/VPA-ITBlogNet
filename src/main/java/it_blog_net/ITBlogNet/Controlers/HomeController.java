package it_blog_net.ITBlogNet.Controlers;

import it_blog_net.ITBlogNet.Models.Post;
import it_blog_net.ITBlogNet.Services.NotificationService;
import it_blog_net.ITBlogNet.Services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private PostService postService ;


    @RequestMapping("/")
    public String  index(Model model){
        List<Post> allpostwithteaser = postService.getPostWithTeasser(null);
        model.addAttribute("allposts", allpostwithteaser);
        return "index";
    }

    @RequestMapping("/expiredSession")
    public String expiredSession() { return "expiredSession";}

    @RequestMapping("/invalidSession")
    public String invalidSession(){return "invalidSession";}



}




