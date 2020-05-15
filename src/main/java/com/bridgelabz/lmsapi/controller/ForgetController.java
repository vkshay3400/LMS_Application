package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.service.LoginUserDetailsService;
import com.bridgelabz.lmsapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgetController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    LoginUserDetailsService service;

    @PutMapping("/change-password")
    public String signUpSuccess(@RequestBody UserDTO userDTO, @RequestParam(value = "token") String token) {
        String id = jwtUtil.extractUserName(token);
        service.changePassword(userDTO);
        return ("Changed password successfully.");
    }
}
