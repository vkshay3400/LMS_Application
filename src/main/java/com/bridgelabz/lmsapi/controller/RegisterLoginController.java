package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.service.LoginUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterLoginController {

    @Autowired
    private LoginUserDetailsService userDetailsService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws Exception{
        return ResponseEntity.ok(userDetailsService.save(userDTO));
    }
}
