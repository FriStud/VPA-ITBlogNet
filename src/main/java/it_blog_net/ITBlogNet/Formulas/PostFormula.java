package it_blog_net.ITBlogNet.Formulas;

import it_blog_net.ITBlogNet.Models.Post;
import it_blog_net.ITBlogNet.Models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PostFormula {

    @NotNull
    @Size(min = 1)
    private String title;

    @NotNull
    @Size(min = 1)
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

