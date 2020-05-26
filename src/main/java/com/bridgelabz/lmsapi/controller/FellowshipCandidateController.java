package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.service.FellowshipCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/fellowship")
public class FellowshipCandidateController {

    @Autowired
    FellowshipCandidateService service;

    // API to put in db
    @PostMapping(value = "/updatedetails")
    public ResponseEntity<String> updatedetails() throws IOException {
        service.getUpdateDetails();
        return new ResponseEntity<>("Imported Successfully", HttpStatus.CREATED);
    }

    // API to get count
    @GetMapping(value = "/count")
    public ResponseEntity<Integer> getCount(){
        Integer value = service.getFellowshipCount();
        return new ResponseEntity<>(value, HttpStatus.CREATED);
    }

}
