package com.session.id.demo.webconfig;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;

import javax.servlet.SessionTrackingMode;
import java.util.EnumSet;

@Configuration
public class SecurityConfiguration {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        DefaultHttpFirewall defaultHttpFirewall = new DefaultHttpFirewall();
        return web -> web.httpFirewall(defaultHttpFirewall);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin( form -> {
                    form.loginPage("/login");
                    form.loginProcessingUrl("/login");
                    form.usernameParameter("username");
                    form.passwordParameter("password");
                    form.defaultSuccessUrl("/", true);
                    form.failureUrl("/login?error");
        });
        http.authorizeRequests(auth -> {
           auth.antMatchers("/login").permitAll();
           auth.anyRequest().authenticated();
        });
        http.sessionManagement().enableSessionUrlRewriting(true);
        http.sessionManagement().sessionFixation().none();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User.withUsername("misaka")
                .password(
                        PasswordEncoderFactories
                                .createDelegatingPasswordEncoder()
                                .encode("mikoto"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public ServletContextInitializer servletContextInitializer(){
        return servletContext -> servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.URL));
    }

}
