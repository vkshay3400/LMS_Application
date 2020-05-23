package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.MessageResponse;
import com.bridgelabz.lmsapi.dto.UserDto;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.AuthenticationResponse;
import com.bridgelabz.lmsapi.model.UserDao;
import com.bridgelabz.lmsapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
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
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto, @RequestParam (value = "id") Long id) {
        if(service.checkUser(userDto)) {
            return new ResponseEntity<>("Login Successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Login Unsuccessful", HttpStatus.BAD_REQUEST);
    }

    // API to send mail
    @PostMapping("/sendmail")
    public ResponseEntity<MessageResponse> sendMail(@RequestBody UserDto userDTO) {
        return new ResponseEntity<>(service.getMail(userDTO),HttpStatus.GONE);
    }

    // API to change password
    @PutMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody UserDto userDto, @RequestParam(value = "token") String token) {
        service.getId(token);
        service.changePassword(userDto);
        return new ResponseEntity<>("Changed password successfully",HttpStatus.CREATED);
    }
}
