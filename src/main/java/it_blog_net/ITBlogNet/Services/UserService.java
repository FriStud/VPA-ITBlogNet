package it_blog_net.ITBlogNet.Services;

import it_blog_net.ITBlogNet.Interfaces.IUserService;
import it_blog_net.ITBlogNet.Models.User;
import it_blog_net.ITBlogNet.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Objects;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isRegisterEmail(String email)
    {
       return this.findByEmail(email) != null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean authenticateUser(String userName, String password) {
        User authUser = findByName(userName);

        if(authUser != null)
            if (passwordEncoder.matches(password,authUser.getPasswordHash()))
                return true;

        return false;
    }

    @Override
    public boolean register(String userName, String passord, String rePass, String email) {
        return !userName.isEmpty() && !passord.isEmpty() && Objects.equals(passord,rePass) && !email.isEmpty(); //not implemented properly unless database conection establishing
    }

    @Override
    public User findByName(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.getOne(id);
    }

    @Override
    public User create(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User edit(User user) {
        return this.userRepository.save(user);
    }



    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
}
