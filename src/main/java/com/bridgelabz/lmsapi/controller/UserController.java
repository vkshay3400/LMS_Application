package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.UserDto;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.AuthenticationResponse;
import com.bridgelabz.lmsapi.dto.LoginDto;
import com.bridgelabz.lmsapi.model.UserDao;
import com.bridgelabz.lmsapi.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "User Details", value = "UserDetails",
        description = "Controller for User Details update")
public class UserController {

    @Autowired
    private UserService service;

    // API for register user
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDao> saveUser(@RequestBody UserDto userDto) {
        return new ResponseEntity(service.registerUser(userDto),HttpStatus.CREATED);
    }

    // API for authenticate user
    @PostMapping(value = "/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String jwt = service.getToken(authenticationRequest);
        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.CREATED);
    }

    // API for login user
    @GetMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        service.checkUser(loginDto);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    // API to send mail
    @PostMapping(value = "/sendmail")
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> sendMail(@RequestBody UserDto userDTO) {
        return new ResponseEntity<>(service.sendMail(userDTO),HttpStatus.GONE);
    }

    // API to change password
    @PutMapping(value = "/changepassword")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> changePassword(@RequestBody UserDto userDto, @RequestParam(value = "token") String token) {
        service.changePassword(userDto,token);
        return new ResponseEntity<>("Changed password successfully",HttpStatus.CREATED);
    }

}
