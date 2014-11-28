package com.ruan.managecare.clients_access;

import com.mongodb.Cursor;
import com.mongodb.DBObject;
import com.ruan.managecare.data.mongodb.MongoDBRequests;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .antMatchers("/", "main").permitAll().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

        http.formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }


    @Configuration
    protected static class AuthConfig extends GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {

            Cursor c = MongoDBRequests.getCred();
            try {
                while (c.hasNext()) {
                    DBObject o = c.next();
                    
                    auth
                            .inMemoryAuthentication()
                            .withUser(o.get("_id").toString())
                            .password(o.get("p").toString())
                            .roles(o.get("role").toString());
                }
            } catch (Exception e) {
            } finally {
                c.close();
            }
        }
    }
}
