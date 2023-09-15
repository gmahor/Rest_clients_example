package com.rest.core.controller;

import com.rest.core.dto.StudentDTO;
import com.rest.core.entity.Student;
import com.rest.core.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(value = "/saveStudent")
    public ResponseEntity<Object> saveStudent(@RequestBody StudentDTO studentDTO) {
        try {
            Student savedStudent = studentService.saveStudent(studentDTO);
            return new ResponseEntity<>(savedStudent, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error save student : ", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<Object> findAllStudents() {
        try {
            List<Student> allStudents = studentService.findAllStudents();
            if (!allStudents.isEmpty()) {
                return new ResponseEntity<>(allStudents, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error fetching students : ", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{school-id}")
    public ResponseEntity<List<Student>> findAllStudents(@PathVariable("school-id") Integer schoolId) {
        try {
            List<Student> allStudents = studentService.findAllStudentsBySchool(schoolId);
            if (!allStudents.isEmpty()) {
                return new ResponseEntity<>(allStudents, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error fetching students by schoolId: ", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
