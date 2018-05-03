package it_blog_net.ITBlogNet.Formulas;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public abstract class UserFormula {

    @Size(min = 1,max = 25,message = "The user's name should be in range 1 -> 25 characters!")
    private String userName;
    @NotNull
    @Size(min = 1)
    private String pass;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
