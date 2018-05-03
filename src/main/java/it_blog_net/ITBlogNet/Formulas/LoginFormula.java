package it_blog_net.ITBlogNet.Formulas;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginFormula extends UserFormula {
    private boolean rememberMe;

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
