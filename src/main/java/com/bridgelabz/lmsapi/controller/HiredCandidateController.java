package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.HiredCandidateDto;
import com.bridgelabz.lmsapi.model.HiredCandidateDao;
import com.bridgelabz.lmsapi.service.HiredCandidateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/hired")
@Api(tags = "Hired Details", value = "HiredDetails",
        description = "Controller for Hired Details update")

public class HiredCandidateController {

    @Autowired
    private HiredCandidateService service;

    // API to put in db
    @PostMapping(value = "/importhiredlist")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> importList(@RequestParam("file") MultipartFile file) {
        service.getHiredCandidate(file);
        return new ResponseEntity<>("Imported Successfully", HttpStatus.CREATED);
    }

    // API to get list
    @GetMapping(value = "/allhiredcandidates")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List> getAllCandidatesDetails() {
        return new ResponseEntity<>(service.getList(), HttpStatus.FOUND);
    }

    // API to get candidate profile
    @GetMapping(value = "/hiredcandidatedetails")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<HiredCandidateDao> getCandidateDetails(@RequestParam(value = "{id:[0-9]}") long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.FOUND);
    }

    // API to send mail to update candidate choice
    @PostMapping(value = "/sendmail")
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> sendMail(@RequestBody HiredCandidateDto hiredCandidateDto) {
        return new ResponseEntity<>(service.sendMail(hiredCandidateDto), HttpStatus.GONE);
    }

    // API to update candidate status
    @PutMapping(value = "/onboardstatus")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> onBoardStatus(@RequestBody HiredCandidateDto hiredCandidateDto,
                                                @RequestParam(value = "{choice:[A-Z,a-z]}") String choice) {
        service.getOnboardStatus(hiredCandidateDto, choice);
        return new ResponseEntity<>("Updated Status successfully", HttpStatus.ACCEPTED);
    }

    // API to update candidate status
    @PostMapping(value = "/sendjoboffer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendJobOffer(@RequestBody HiredCandidateDto hiredCandidateDto) {
        String mailMessage = service.sendJobOffer(hiredCandidateDto);
        return new ResponseEntity<>(mailMessage, HttpStatus.OK);
    }

}
