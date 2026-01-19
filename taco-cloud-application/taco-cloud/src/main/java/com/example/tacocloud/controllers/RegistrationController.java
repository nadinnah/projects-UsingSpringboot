package com.example.tacocloud.controllers;

import com.example.tacocloud.models.RegistrationForm;
import com.example.tacocloud.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    //constructor injection: This controller CANNOT exist unless you give me a UserRepository and a PasswordEncoder
    // Spring needs it to know what your class depends on.
    //ex: new RegistrationController(userRepoBean, passwordEncoderBean); conceptually
    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo=userRepo;
        this.passwordEncoder=passwordEncoder;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form){
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
