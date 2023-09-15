package com.rest.core.service;

import com.rest.core.dto.SchoolDTO;
import com.rest.core.dto.StudentDTO;
import com.rest.core.entity.Student;
import com.rest.core.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final RestTemplate restTemplate;

    private final SchoolClientService schoolClientService;

    private final WebClient webClient;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          RestTemplate restTemplate,
                          SchoolClientService schoolClientService,
                          WebClient webClient) {
        this.studentRepository = studentRepository;
        this.restTemplate = restTemplate;
        this.schoolClientService = schoolClientService;
        this.webClient = webClient;
    }


    public Student saveStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFirstname(studentDTO.getFirstname());
        student.setLastname(studentDTO.getLastname());
        student.setEmail(studentDTO.getEmail());
//        student.setSchoolId(studentDTO.getSchoolId());
//        getSchoolBySchoolId(studentDTO.getSchoolId());
//        getSchool(studentDTO.getSchoolId());
        SchoolDTO schoolDTO = getSchoolWithWebClient(studentDTO.getSchoolId());
        student.setSchoolId(schoolDTO != null ? schoolDTO.getId() : 0);
        return studentRepository.save(student);
    }

    // With Rest Template
    private void getSchoolBySchoolId(long id) {
        ResponseEntity<SchoolDTO> response = restTemplate.getForEntity("http://localhost:8081/api/v1/schools/" + id, SchoolDTO.class);
        if (response.getStatusCode().value() == 200) {
            SchoolDTO body = response.getBody();
            log.info("Body : {}", body);
        }
    }

    private void getSchool(long id) {
        SchoolDTO schoolDTO = schoolClientService.getSchoolById(id);
        if (schoolDTO != null) {
            log.info("School Dto : {}", schoolDTO);
        }
    }

    private SchoolDTO getSchoolWithWebClient(long id) {
        try {
            return webClient.get().uri("/" + id).retrieve().bodyToMono(SchoolDTO.class).block();
        } catch (Exception e) {
            log.info("Error while fetching school by sch_id - ", e);
        }
        return null;
    }

    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> findAllStudentsBySchool(Integer schoolId) {
        return studentRepository.findAllBySchoolId(schoolId);
    }

}
