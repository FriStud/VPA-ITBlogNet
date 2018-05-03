package it_blog_net.ITBlogNet.Models;

import it_blog_net.ITBlogNet.Utils.Validation.ValidEmail;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 25, unique = true)
    private String username;

    @Column(length = 60)
    private String passwordHash;

    @Column(length = 100)
    private String fullName;

    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();

    @Column(nullable = false, unique = true )
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", fullName='" + fullName + '\''+'}';
    }

    public User(){}

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public User(String username, String mail, String passwordHash)
    {
        this(username,passwordHash);
        this.email = mail;
    }

    public User( String username, String mail,String fullName, String passwordHash) {
        this(username,mail,passwordHash);
        this.fullName = fullName;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }



    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Post> getPosts() {
        return posts;
    }
}
