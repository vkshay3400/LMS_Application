package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.configuration.ApplicationConfig;
import com.bridgelabz.lmsapi.dto.HiredCandidateDto;
import com.bridgelabz.lmsapi.dto.ResponseDto;
import com.bridgelabz.lmsapi.service.HiredCandidateService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping(value = "/hired")
@Api(tags = "Hired Details", value = "HiredDetails", description = "Controller for Hired Details update")
public class HiredCandidateController {

    @Autowired
    private HiredCandidateService service;

    /**
     * API to save in db
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/importhiredlist")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> importList(@RequestParam("file") MultipartFile file) {
        boolean imported = service.getHiredCandidate(file);
        return new ResponseEntity<>(new ResponseDto(imported, ApplicationConfig
                .getMessageAccessor().getMessage("106")), HttpStatus.CREATED);
    }

    /**
     * API to get list
     *
     * @return
     */
    @GetMapping(value = "/allhiredcandidates")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List> getAllCandidatesDetails() {
        List list = service.getList();
        return new ResponseEntity<List>(list, HttpStatus.FOUND);
    }

    /**
     * API to get candidate profile
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/hiredcandidatedetails")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ResponseDto> getCandidateDetails(@RequestParam(value = "{id:[0-9]}") long id) {
        return new ResponseEntity<>(new ResponseDto(service.findById(id), ApplicationConfig
                .getMessageAccessor().getMessage("107")), HttpStatus.FOUND);
    }

    /**
     * API to send mail to update candidate choice
     *
     * @param hiredCandidateDto
     * @return
     */
    @PostMapping(value = "/sendmail")
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<ResponseDto> sendMail(@RequestBody HiredCandidateDto hiredCandidateDto) throws MessagingException {
        return new ResponseEntity<>(new ResponseDto(service.sendMail(hiredCandidateDto), ApplicationConfig
                .getMessageAccessor().getMessage("104")),HttpStatus.GONE);
    }

    /**
     * API to update candidate status
     *
     * @param email
     * @param choice
     * @return
     */
    @PutMapping(value = "/onboardstatus")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> onBoardStatus(@RequestParam(value = "email") String email,
                                                @RequestParam(value = "choice") String choice) {
        service.getOnboardStatus(email, choice);
        return new ResponseEntity<>("Updated Status successfully", HttpStatus.ACCEPTED);
    }

    /**
     * API to update candidate status
     *
     * @param hiredCandidateDto
     * @return
     */
    @PostMapping(value = "/sendjoboffer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> sendJobOffer(@RequestBody HiredCandidateDto hiredCandidateDto) {
        String mailMessage = service.sendJobOffer(hiredCandidateDto);
        return new ResponseEntity<>(new ResponseDto(mailMessage, ApplicationConfig
                .getMessageAccessor().getMessage("108")), HttpStatus.OK);
    }

}
