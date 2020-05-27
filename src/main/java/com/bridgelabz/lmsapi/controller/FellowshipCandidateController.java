package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.service.FellowshipCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/fellowship")
public class FellowshipCandidateController {

    @Autowired
    FellowshipCandidateService service;

    // API to put in db
    @PostMapping(value = "/getdetails")
    public ResponseEntity<String> getDetails() throws IOException {
        service.getDetails();
        return new ResponseEntity<>("Imported Successfully", HttpStatus.CREATED);
    }

    // API to get count
    @GetMapping(value = "/count")
    public ResponseEntity<Integer> getCount() {
        Integer value = service.getFellowshipCount();
        return new ResponseEntity<>(value, HttpStatus.CREATED);
    }

    // API to put in db
    @PutMapping(value = "/updatedetails")
    public ResponseEntity<String> updateDetails(@RequestBody PersonalDetailsDto personalDetailsDto,
                                                @RequestParam("id") long id) throws IOException {
        service.getUpdateDetails(personalDetailsDto, id);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.CREATED);
    }

}
