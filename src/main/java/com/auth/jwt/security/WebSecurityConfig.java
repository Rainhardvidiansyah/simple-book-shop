package com.auth.jwt.security;

import com.auth.jwt.repository.UserRepo;
import com.auth.jwt.security.jwt.AuthEntry;
import com.auth.jwt.security.jwt.AuthTokenFilter;
import com.auth.jwt.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepo userRepo;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntry authEntry;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }

    @Autowired
    public WebSecurityConfig(UserRepo userRepo, UserDetailsServiceImpl userDetailsService, AuthEntry authEntry) {
        this.userRepo = userRepo;
        this.userDetailsService = userDetailsService;
        this.authEntry = authEntry;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
       return super.authenticationManager();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntry)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/registration").permitAll()
                .antMatchers("/api/v1/auth/login").permitAll()
                .anyRequest().authenticated();
                http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/swagger/ui/",
                        "/configuration/ui", "/swagger-resources/**",
                        "/configuration/security", "/swagger-ui.html",
                        "/swagger-ui/**", "/webjars/**");
    }
}
