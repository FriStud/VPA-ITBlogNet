package it_blog_net.ITBlogNet.Components;


import it_blog_net.ITBlogNet.Models.User;
import it_blog_net.ITBlogNet.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SessionInfo {

    private User loggedUser;

    @Autowired
    private UserService userService;

    protected User getLoggedUser()
    {
        if(loggedUser == null)
        {
            loggedUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return  loggedUser;
    }

    protected  void setLoggedUser(User user)
    {
        this.loggedUser = user;
    }


}
