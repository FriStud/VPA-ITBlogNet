package it_blog_net.ITBlogNet.Interfaces;
import it_blog_net.ITBlogNet.Models.User;
import java.util.List;

public interface IUserService {
    boolean authenticateUser(String userName, String password);
    boolean register(String userName, String passord, String rePass, String email);
    User findByName(String userName);
    List<User> findAll();
    User findById(Long id);
    User create(User user);
    User edit(User user);
    User findByEmail(String email);
    void deleteById(Long id);
}

