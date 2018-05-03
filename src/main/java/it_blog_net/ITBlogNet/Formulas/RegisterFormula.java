package it_blog_net.ITBlogNet.Formulas;

import it_blog_net.ITBlogNet.Utils.Validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterFormula extends UserFormula{

    @NotNull
    @Size(min = 1)
    private String rePass;

    @ValidEmail
    @NotNull
    private String email;

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
