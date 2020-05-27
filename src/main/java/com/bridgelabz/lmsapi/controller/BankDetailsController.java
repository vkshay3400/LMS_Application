package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.BankDetailsDto;
import com.bridgelabz.lmsapi.service.BankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bankdetails")
public class BankDetailsController {

    @Autowired
    private BankDetailsService service;

    // API for register user
    @PostMapping(value = "/update")
    public ResponseEntity<String> updateDetails(@RequestBody BankDetailsDto bankDetailsDto) {
        service.saveBankDetails(bankDetailsDto);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);
    }
}
