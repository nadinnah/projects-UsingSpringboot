package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name= "Student")
public class Student {

    @Id
    private Long id;
    private String firstName;
    private Integer age;

    public Student(Integer age, String firstName, Long id) {
        this.age = age;
        this.firstName = firstName;
        this.id = id;
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
                '}';
    }
}
