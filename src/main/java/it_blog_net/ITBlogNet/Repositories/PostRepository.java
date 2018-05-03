package it_blog_net.ITBlogNet.Repositories;

import it_blog_net.ITBlogNet.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
    @Query("SELECT p FROM Post  p LEFT JOIN FETCH p.author ORDER BY p.date DESC")
    List<Post> findLatest5Posts();

    @Query("select p from Post p where p.author.id =:userName")
    List<Post> findAllUsersPost(@Param("userName") Long id);

}

