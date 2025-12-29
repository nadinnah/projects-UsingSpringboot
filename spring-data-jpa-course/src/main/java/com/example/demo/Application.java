package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //to have code running after application startup
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        return args->{
            Student nad= new Student("Nadin", 23,"nadinahmedmohamed@gmail.com");
            studentRepository.save(nad);
        };
    }
}
