package com.example.demo;

import com.example.demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> { //id is of type long


}
