package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.model.CandidateDao;
import com.bridgelabz.lmsapi.service.HiredCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hired")
public class HiredCandidateController {

    @Autowired
    private HiredCandidateService service;

    // API to put in db
    @PostMapping("/importhiredlist")
    public ResponseEntity<String > importList(@RequestParam("file") MultipartFile file) throws IOException {
        service.getHiredCandidate(file);
        return new ResponseEntity<>( "Imported Successfully", HttpStatus.CREATED);
    }

    // API to get list
    @GetMapping("/allhiredcandidates")
    public ResponseEntity<List> getAllCandidatesDetails() {
        return new ResponseEntity<>( service.getList(),HttpStatus.FOUND);
    }

    // API to get candidate profile
    @GetMapping("/hiredcandidatedetails")
    public ResponseEntity<Optional<CandidateDao>> getCandidateDetails(@RequestParam (value = "id") Long id) throws IOException{
        return new ResponseEntity<>(service.findById(id),HttpStatus.FOUND);
    }
}
