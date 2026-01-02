package com.example.demo;

import com.example.demo.models.Course;
import com.example.demo.models.Student;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

       ApplicationContext context= SpringApplication.run(Application.class, args);

        //Student student1= context.getBean(Student.class);

    }

    //to have code running after application startup
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, CourseRepository courseRepository){
        return args->{
            Student nad= new Student("Nadin", 23,"nadinahmedmohamed@gmail.com");
            studentRepository.save(nad);
            Course course= new Course("CSE2323", "computer programming");
            courseRepository.save(course);
        };
    }
}
