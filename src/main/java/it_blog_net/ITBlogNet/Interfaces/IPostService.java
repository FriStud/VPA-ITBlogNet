package it_blog_net.ITBlogNet.Interfaces;


import it_blog_net.ITBlogNet.Models.Post;

import java.util.List;

public interface IPostService {

    List<Post> findPostByUser(Long id);
    List<Post> findAll();
    List<Post> findLatest5();
    Post findById(Long id);
    Post create(Post post);
    Post edit(Post post);
    void deleteById(Long id);
}

