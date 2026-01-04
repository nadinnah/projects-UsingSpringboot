package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Setter
@Getter
@Entity(name= "Student")
@Table(
        name= "Student",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "student_name_unique",
                        columnNames = "email"
                )
        }
)
@Component
public class Student {

    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1 //start 1 increment 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "student_sequence" //like sequenceName in SequenceGenerator.
    )
    @Column(
            name ="id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable= false,
            columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "age",
            nullable= false,
            columnDefinition = "INT"
    )
    private Integer age;

    @Column(
            name = "email",
            nullable= false,
            columnDefinition = "TEXT"

    )
    private String email;

    //All or partial args constructor FOR Clean object creation
    public Student(
                   String firstName,
                   Integer age, String email) {
        this.firstName = firstName;
        this.age = age;
        this.email=email;
    }

    public Student() {
        //JPA (Hibernate) must be able to create your entity using reflection(inspect classes at runtime, create objects, access fields and methods).
        //That only works if a no-argument constructor exists.
        //Can be protected (recommended)
        //Should not contain logic
        //This constructor is for Hibernate, not for you.
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    private List<Course> courses= new ArrayList<>();


}
