package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.service.UploadDocumentsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/upload")
@Api(tags = "Upload Documents", value = "UploadDocuments", description = "Controller for Upload Documents")
public class UploadDocumentsController {

    @Autowired
    private UploadDocumentsService service;

    // API for upload documents
    @PostMapping(value = "/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "id") Long id) throws Exception {
        service.upload(file, id);
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

}
