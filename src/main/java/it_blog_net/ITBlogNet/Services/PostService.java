package it_blog_net.ITBlogNet.Services;

import it_blog_net.ITBlogNet.Interfaces.IPostService;
import it_blog_net.ITBlogNet.Models.Post;
import it_blog_net.ITBlogNet.Models.User;
import it_blog_net.ITBlogNet.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
public class PostService implements IPostService {

    public static int NUMBER_TEASER = 20;

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findPostByUser(Long id) {
        return  postRepository.findAllUsersPost(id);
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public List<Post> findLatest5() {
        return postRepository.findLatest5Posts();
    }

    @Override
    public Post findById(Long id) {
        return this.postRepository.getOne(id);
    }

    @Override
    public Post create(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public Post edit(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepository.deleteById(id);
    }

    public List<Post> getPostWithTeasser(Long id)
    {
        List<Post> postToCreateTeaser = null;

        if(id == null)
            postToCreateTeaser = findAll();
        else
            postToCreateTeaser = findPostByUser(id);

        if (postToCreateTeaser.size() > 0)
            for (Post p: postToCreateTeaser)
                p.setTesser(makeStrictTeaser(p.getBody()));

        return postToCreateTeaser;
    }

    private String makeStrictTeaser(String makeFrom)
    {
        String [] splis = makeFrom.split("\\s+");

        String teaserFromSplits = "";

        for ( int i = 0; i < NUMBER_TEASER; i ++)
        {
            if(i == splis.length)
                break;

            teaserFromSplits += splis[i].trim() +" ";
        }

        return teaserFromSplits +"...";
    }
}
