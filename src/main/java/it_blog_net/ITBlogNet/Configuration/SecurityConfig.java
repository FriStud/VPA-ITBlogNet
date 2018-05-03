package it_blog_net.ITBlogNet.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher()
    {
        return  new HttpSessionEventPublisher();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(2)
                .expiredUrl("/expiredSession.html");

        security.sessionManagement()
                .invalidSessionUrl("/invalidSession.html")
                .sessionFixation()
                .migrateSession();

        security
                .authorizeRequests()
                .anyRequest()
                .permitAll()
//                .and().httpBasic()
//                .and()
//                    .formLogin()
//                    .loginPage("/users/login").permitAll()
//                    .loginProcessingUrl("/users/login")
//                .and()
//                    .logout().permitAll().logoutUrl("/users/logout");
        ;

        security.csrf().disable();


    }
}