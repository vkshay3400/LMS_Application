package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.service.FellowshipCandidateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/fellowship")
@Api(tags = "Fellowship Details", value = "FellowshipDetails",
        description = "Controller for Fellowship Details update")
public class FellowshipCandidateController {

    @Autowired
    FellowshipCandidateService service;

    // API to save in db
    @PostMapping(value = "/details")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> details() {
        service.getDetails();
        return new ResponseEntity<>("Imported Successfully", HttpStatus.CREATED);
    }

    // API to get count of fellowship candidates
    @GetMapping(value = "/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> count() {
        Integer value = service.getFellowshipCount();
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    // API to save personal details in db
    @PutMapping(value = "/updatedetails")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> updateDetails(@RequestBody PersonalDetailsDto personalDetailsDto,
                                                @RequestParam("{id:[0-9]}") long id) {
        service.getUpdateDetails(personalDetailsDto, id);
        return new ResponseEntity<>("Updated details successfully", HttpStatus.CREATED);
    }

    // API for upload documents
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "id") Long id) throws Exception {
        service.upload(file, id);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

}
