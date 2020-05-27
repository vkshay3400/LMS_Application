package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.CandidateQualificationDto;
import com.bridgelabz.lmsapi.service.CandidateQualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/qualificationdetails")
public class CandidateQualificationController {

    @Autowired
    private CandidateQualificationService service;

    // API for education update
    @PostMapping(value = "/update")
    public ResponseEntity<String> updateQualification(@RequestBody CandidateQualificationDto candidateQualificationDto) {
        service.saveEducationDetails(candidateQualificationDto);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);
    }
}
