package com.example.tacocloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web
        .configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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