package com.example.demo.controllers;
import com.example.demo.models.Course;
import com.example.demo.models.Student;
import com.example.demo.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/courses")
    public List<Course> getCourses(){
        return courseService.fetchCourses();
    }

    @PostMapping("/courses/{courseId}/students/{studentId}")
    public Course addStudentToCourse(@PathVariable Long studentId, @PathVariable String courseId){
    return courseService.assignStudentToCourse(studentId,courseId);
    }
}
