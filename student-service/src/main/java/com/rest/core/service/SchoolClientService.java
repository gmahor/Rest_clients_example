package com.rest.core.service;

import com.rest.core.dto.SchoolDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "school", url = "http://localhost:8081")
public interface SchoolClientService {

    @GetMapping(value = "/api/v1/schools/{id}")
    SchoolDTO getSchoolById(@PathVariable("id") Long id);


}
