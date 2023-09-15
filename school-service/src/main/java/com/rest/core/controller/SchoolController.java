package com.rest.core.controller;

import com.rest.core.dto.SchoolDTO;
import com.rest.core.entity.School;
import com.rest.core.service.SchoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/schools")
@CrossOrigin("*")
public class SchoolController {


    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping("/saveSchool")
    public ResponseEntity<Object> saveSchool(@RequestBody SchoolDTO schoolDTO) {
        try {
            School savedSchoolObj = schoolService.saveSchool(schoolDTO);
            log.info("School saved successfully");
            return new ResponseEntity<>(savedSchoolObj, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error save school : ", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("")
    public ResponseEntity<Object> getSchools() {
        try {
            List<School> allSchools = schoolService.getAllSchools();
            if (!allSchools.isEmpty()) {
                log.info("Schools fetched successfully");
                return new ResponseEntity<>(allSchools, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error fetching schools : ", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getSchoolById(@PathVariable("id") Long id) {
        try {
            School school = schoolService.getSchoolById(id);
            if (school != null) {
                log.info("School fetched successfully");
                return new ResponseEntity<>(school, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error fetching school : ", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
