package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.configuration.ApplicationConfig;
import com.bridgelabz.lmsapi.dto.*;
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

    /**
     * API to save in db
     *
      * @return
     */
    @PostMapping(value = "/details")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> details() {
        boolean details = service.getDetails();
        return new ResponseEntity<>(new ResponseDto(details, ApplicationConfig
                .getMessageAccessor().getMessage("106")), HttpStatus.CREATED);
    }

    /**
     * API to get count of fellowship candidates
     *
     * @return
     */
    @GetMapping(value = "/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> count() {
        Integer value = service.getFellowshipCount();
        return new ResponseEntity<>(new ResponseDto(value, ApplicationConfig
                .getMessageAccessor().getMessage("109")),HttpStatus.OK);
    }

    /**
     * API to save personal details in db
     *
     * @param personalDetailsDto
     * @param id
     * @return
     */
    @PutMapping(value = "/updatedetails")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> updateDetails(@RequestBody PersonalDetailsDto personalDetailsDto,
                                                @RequestParam("id") long id) {
        boolean updateDetails = service.getUpdateDetails(personalDetailsDto, id);
        return new ResponseEntity<>(new ResponseDto(updateDetails, ApplicationConfig
        .getMessageAccessor().getMessage("110")), HttpStatus.CREATED);
    }

    /**
     * API for register user
     *
     * @param bankDetailsDto
     * @return
     */
    @PostMapping("/updatebankdetails")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> updateDetails(@RequestBody BankDetailsDto bankDetailsDto) {
        boolean bankDetails = service.saveBankDetails(bankDetailsDto);
        return new ResponseEntity<>(new ResponseDto(bankDetails, ApplicationConfig
        .getMessageAccessor().getMessage("111")), HttpStatus.CREATED);
    }

    /**
     * API for education update
     *
     * @param candidateQualificationDto
     * @return
     */
    @PostMapping("/updatequalification")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> updateQualification(@RequestBody CandidateQualificationDto candidateQualificationDto) {
        boolean  educationDetails = service.saveEducationDetails(candidateQualificationDto);
        return new ResponseEntity<>(new ResponseDto(educationDetails, ApplicationConfig
                .getMessageAccessor().getMessage("112")), HttpStatus.CREATED);
    }

    /**
     * API for upload documents in system
     *
     * @param file
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> uploadFileInSystem(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "id") Long id) throws Exception {
        boolean upload = service.upload(file, id);
        return new ResponseEntity(new ResponseDto(upload, ApplicationConfig
                .getMessageAccessor().getMessage("113")), HttpStatus.GONE);
    }


    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseDto> uploadFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam(value = "updateDocumentDto") String updateDocumentDto) {
        String url = service.uploadFile(file, updateDocumentDto);
        return new ResponseEntity(new ResponseDto(url, ApplicationConfig
        .getMessageAccessor().getMessage("113")), HttpStatus.GONE);
    }

}
