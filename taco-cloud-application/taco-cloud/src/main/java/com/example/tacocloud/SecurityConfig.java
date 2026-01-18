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
//            @Autowired
//            DataSource dataSource; //DataSource so that it knows how to access the database

    //Uses JDBC authentication
    //Reads users from the database
    //Uses Spring Security’s default queries
    //Does NOT insert or modify users
//            @Bean
//            public UserDetailsService userDetailsService(){
//                JdbcUserDetailsManager manager= new JdbcUserDetailsManager(dataSource);
//
//                manager.setUsersByUsernameQuery(
//                        "select username, password, enabled from Users where username=?"
//                );
//
//                manager.setAuthoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities where username=?"
//                );
//
//                return manager;
//            }

//    @Autowired
//    UserDetailsService userDetailsService;


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
    //Authentication is handled separately by:
    //UserDetailsService
    //PasswordEncoder
    //Spring connects them automatically.

    @Bean
    public SecurityFilterChain secureRequests(HttpSecurity http) throws Exception{

        //order of these rules is important.
        http.authorizeHttpRequests(auth->auth
                        .requestMatchers("/design", "/orders")
                        .hasRole("USER")
                        .requestMatchers("/", "/**").permitAll())
                .formLogin(form->form.loginPage("/login"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()  // allow H2 console ,Anyone can access
                        .anyRequest().authenticated()                   // other endpoints require authentication
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")    // disable CSRF for H2 console
                ) // csrf= An attacker tricks a logged-in user’s browser into sending a fake request.
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)    // allow frames for H2 console
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        return http.build();

        //Security rules declared first take precedence
        //over those declared lower down. If you were to swap the order of those two security
        //rules, all requests would have permitAll() applied to them; the rule for /design and
        ///orders requests would have no effect.
    }

//                    @Bean
//                    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//                        http
//                                .authorizeHttpRequests(auth -> auth
//                                        .requestMatchers("/h2-console/**").permitAll()  // allow H2 console ,Anyone can access
//                                        .anyRequest().authenticated()                   // other endpoints require authentication
//                                )
//                                .csrf(csrf -> csrf
//                                        .ignoringRequestMatchers("/h2-console/**")    // disable CSRF for H2 console
//                                ) // csrf= An attacker tricks a logged-in user’s browser into sending a fake request.
//                                .headers(headers -> headers
//                                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)    // allow frames for H2 console
//                                )
//                                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);             // default login page for other endpoints
//
//                        return http.build();
//                    }

    //AuthenticationManagerBuilder = the guard’s instruction manual
    //userDetailsService = a phonebook where the guard looks up users
    //spring now looks around and says:
    // “I see one UserDetailsService”
    //“I see one PasswordEncoder”
    //“Great, I’ll wire authentication myself”
//            @Bean
//            public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//                http.
//                        authorizeHttpRequests(auth-> auth
//                                .anyRequest().authenticated() //Every HTTP request must come from a logged-in user.
//                        )
//                        .formLogin(form->{});//Use a login form when authentication is needed. You are NOT authenticating users here — just enabling the login method
//                return http.build();
//                //Converts your rules into a SecurityFilterChain
//                //That chain is what actually runs for every request
//                //(Spring Security works as a filter chain)
//
//            }
    //HttpSecurity: A list of rules for incoming HTTP requests
    //Why do we configure security using http now? Because security happens at the HTTP level
    //Browser → Security Filters → Controller
    //now spring says: Configure security by describing how HTTP requests should be handled.
}

//HttpSecurity defines rules for HTTP requests; authentication happens automatically using your beans.

//example in real life:
//UserDetailsService → list of employees
//PasswordEncoder → how IDs are verified
//HttpSecurity → security rules for doors
//formLogin() → reception desk for visitors