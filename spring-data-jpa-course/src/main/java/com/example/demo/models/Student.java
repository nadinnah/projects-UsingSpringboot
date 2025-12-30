package com.example.demo.models;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
