package com.example.tacocloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //in-memory user store, suitable for testing/small apps
    //but customers won't be able to register with the application and manage their own user accounts.
    @Bean
    public InMemoryUserDetailsManager userDetailService(PasswordEncoder encoder){
        UserDetails user1= User.withUsername("buzz").password(encoder.encode("password")).authorities("ROLE_USER").build();
        UserDetails user2= User.withUsername("woody").password(encoder.encode("password")).authorities("ROLE_USER").build();
        return new InMemoryUserDetailsManager(user1,user2);
    }

    //jdbc-based user store
    @Autowired
    DataSource dataSource; //DataSource so that it knows how to access the database

    //Uses JDBC authentication
    //Reads users from the database
    //Uses Spring Security’s default queries
    //Does NOT insert or modify users
    @Bean
    public UserDetailsService userDetailsService(){
        JdbcUserDetailsManager manager= new JdbcUserDetailsManager(dataSource);

        manager.setUsersByUsernameQuery(
                "select username, password, enabled from Users where username=?"
        );

        manager.setAuthoritiesByUsernameQuery(
                "select username, authority from UserAuthorities where username=?"
        );

        return manager;
    }

    @Autowired
    private UserDetailsService userDetailsService;


    //Use UserDetailsService when you only authenticate
    //Use UserDetailsManager when you want to create / update / delete users
//    @Bean
//    public UserDetailsManager users(PasswordEncoder encoder){
//        UserDetails user1= User.withUsername("buzz").password(encoder.encode("password")).authorities("ROLE_USER").build();
//        UserDetails user2= User.withUsername("woody").password(encoder.encode("password")).authorities("ROLE_USER").build();
//        JdbcUserDetailsManager users= new JdbcUserDetailsManager(dataSource);
//        users.createUser(user1);
//        users.createUser(user2);
//        return users;
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()  // allow H2 console
                        .anyRequest().authenticated()                   // other endpoints require authentication
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")    // disable CSRF for H2 console
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)    // allow frames for H2 console
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);             // default login page for other endpoints

        return http.build();
    }
}