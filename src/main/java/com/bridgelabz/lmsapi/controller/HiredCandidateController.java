package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.HiredCandidateDto;
import com.bridgelabz.lmsapi.model.HiredCandidateDao;
import com.bridgelabz.lmsapi.service.HiredCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/hired")
public class HiredCandidateController {

    @Autowired
    private HiredCandidateService service;

    // API to put in db
    @PostMapping(value = "/importhiredlist")
    public ResponseEntity<String> importList(@RequestParam("file") MultipartFile file) {
        service.getHiredCandidate(file);
        return new ResponseEntity<>("Imported Successfully", HttpStatus.CREATED);
    }

    // API to get list
    @GetMapping(value = "/allhiredcandidates")
    public ResponseEntity<List> getAllCandidatesDetails() {
        return new ResponseEntity<>(service.getList(), HttpStatus.FOUND);
    }

    // API to get candidate profile
    @GetMapping(value = "/hiredcandidatedetails")
    public ResponseEntity<HiredCandidateDao> getCandidateDetails(@RequestParam(value = "id") long id) {
        return new ResponseEntity<HiredCandidateDao>(service.findById(id), HttpStatus.FOUND);
    }

    // API to send mail to update candidate choice
    @PostMapping(value = "/sendmail")
    public ResponseEntity<String> sendMail(@RequestBody HiredCandidateDto hiredCandidateDto) {
        return new ResponseEntity<>(service.sendMail(hiredCandidateDto), HttpStatus.GONE);
    }

    // API to update candidate status
    @PutMapping(value = "/onboradstatus")
    public ResponseEntity<String> onBoradStatus(@RequestBody HiredCandidateDto hiredCandidateDto,
                                                @RequestParam(value = "choice") String choice) {
        service.getOnboardStatus(hiredCandidateDto, choice);
        return new ResponseEntity<>("Updated Status successsfully", HttpStatus.ACCEPTED);
    }

    // API to update candidate status
    @PostMapping(value = "/sendjoboffer")
    public ResponseEntity<String> sendJobOffer(@RequestBody HiredCandidateDto hiredCandidateDto) {
        String mailMessage = service.sendJobOffer(hiredCandidateDto);
        return new ResponseEntity<>(mailMessage, HttpStatus.OK);
    }
}
