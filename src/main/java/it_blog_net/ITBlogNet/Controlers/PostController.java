package it_blog_net.ITBlogNet.Controlers;

import it_blog_net.ITBlogNet.Formulas.LoginFormula;
import it_blog_net.ITBlogNet.Formulas.PostFormula;
import it_blog_net.ITBlogNet.Models.Post;
import it_blog_net.ITBlogNet.Models.User;
import it_blog_net.ITBlogNet.Services.NotificationService;
import it_blog_net.ITBlogNet.Services.PostService;
import it_blog_net.ITBlogNet.Services.UserService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model, HttpSession ssesion)
    {

        if(ssesion.getAttribute("loggedUser") == null)
        {
            notificationService.addErrorMessage("If You Want To Read Article You Have To Be Logged In!");
            return "redirect:/users/login";
        }

        Post post = postService.findById(id);

        if (post == null)
        {
            notificationService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }

       model.addAttribute("post", post);
       return "posts/view";
    }

    @GetMapping("/posts/edit/{id}")
    public String editView(@PathVariable("id") Long id, Model model)
    {
        Post post = postService.findById(id);

        model.addAttribute("editpost", postService.findById(id));
        return "posts/edit";
    }

    @PostMapping("/posts/edit/{id}")
    public String saveEditedViewPost(@PathVariable("id") Long id, @ModelAttribute(value = "editpost") Post editedPost
                                    ,BindingResult bindingResult, ModelMap modelMap, HttpSession session)
    {

        if(bindingResult.hasErrors() || editedPost.getTitle().isEmpty() || editedPost.getBody().isEmpty()) {
            notificationService.addErrorMessage("Empty Title Or Body of article!");
        }
        editedPost.setAuthor((User)session.getAttribute("loggedUser"));
        postService.edit(editedPost);
        notificationService.addInfoMessage("You have Successfully Updated Your Article");
        return "redirect:/blog/blog";
    }

    @GetMapping("/posts/deletepost/{id}")
    public String deletePost(@PathVariable("id") Long id)
    {

        notificationService.addInfoMessage("You have successfully deleted your article!");
        postService.deleteById(id);
        return "redirect:/blog/blog";
    }

    @GetMapping("/posts/create")
    public String createPost(PostFormula postFormula, HttpSession session)
    {
        return "/posts/create";
    }

    @PostMapping("/posts/create")
    public String postCreatePost(PostFormula postFormula, HttpSession session,BindingResult bindingResult)
    {

        if(bindingResult.hasErrors() || postFormula.getTitle().isEmpty() || postFormula.getBody().isEmpty())
        {
            notificationService.addErrorMessage("Empty Title Or Body of article!");
            return "/posts/create";
        }


        Post newPost = new Post(postFormula.getTitle(),postFormula.getBody(),(User)session.getAttribute("loggedUser"));

        postService.create(newPost);

        notificationService.addInfoMessage("New Post Sucessfully Added!");
        return "redirect:/blog/blog";
    }

    @GetMapping("/users/userPost/{id}")
    public String allUserPost(@PathVariable("id") Long id, ModelMap modelMap)
    {
        modelMap.addAttribute("selectedUser", userService.findById(id).getUsername());
        modelMap.addAttribute("usersPosts", postService.getPostWithTeasser(id));
        return "/users/userPost";
    }


}

