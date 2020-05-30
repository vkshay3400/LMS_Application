package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.CandidateQualificationDto;
import com.bridgelabz.lmsapi.service.CandidateQualificationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/update/qualification")
@Api(tags = "Qualification Details", value = "QualificationDetails",
        description = "Controller for Qualification Details update")
public class CandidateQualificationController {

    @Autowired
    private CandidateQualificationService service;

    // API for education update
    @PostMapping //(value = "/update")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateQualification(@RequestBody CandidateQualificationDto candidateQualificationDto) {
        service.saveEducationDetails(candidateQualificationDto);
        return new ResponseEntity<>("Updated qualification details successfully", HttpStatus.CREATED);
    }

}
