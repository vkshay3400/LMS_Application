package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.service.FellowshipCandidateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fellowship")
@Api(tags = "Fellowship Details", value = "FellowshipDetails",
        description = "Controller for Fellowship Details update")
public class FellowshipCandidateController {

    @Autowired
    FellowshipCandidateService service;

    // API to put in db
    @PostMapping(value = "/getdetails")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> getDetails() {
        service.getDetails();
        return new ResponseEntity<>("Imported Successfully", HttpStatus.CREATED);
    }

    // API to get count
    @GetMapping(value = "/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getCount() {
        Integer value = service.getFellowshipCount();
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    // API to put in db
    @PutMapping(value = "/updatedetails")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateDetails(@RequestBody PersonalDetailsDto personalDetailsDto,
                                                @RequestParam("{id:[0-9]}") long id) {
        service.getUpdateDetails(personalDetailsDto, id);
        return new ResponseEntity<>("Updated details successfully", HttpStatus.CREATED);
    }

}
