package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.UserDto;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.AuthenticationResponse;
import com.bridgelabz.lmsapi.model.LoginDto;
import com.bridgelabz.lmsapi.model.UserDao;
import com.bridgelabz.lmsapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService service;

    // API for register user
    @PostMapping(value = "/register")
    public ResponseEntity<UserDao> saveUser(@RequestBody UserDto userDto) throws Exception {
        return new ResponseEntity(service.registerUser(userDto),HttpStatus.CREATED);
    }

    // API for authenticate user
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        String jwt = service.getToken(authenticationRequest);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.CREATED);
    }

    // API for login user
    @GetMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        boolean userLogin = service.checkUser(loginDto);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    // API to send mail
    @PostMapping(value = "/sendmail")
    public ResponseEntity<String> sendMail(@RequestBody UserDto userDTO) {
        return new ResponseEntity<>(service.getMail(userDTO),HttpStatus.GONE);
    }

    // API to change password
    @PutMapping(value = "/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody UserDto userDto, @RequestParam(value = "token") String token) {
        service.changePassword(userDto,token);
        return new ResponseEntity<>("Changed password successfully",HttpStatus.CREATED);
    }
}
