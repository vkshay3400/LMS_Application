package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.Response;
import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.AuthenticationResponse;
import com.bridgelabz.lmsapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping("/login")
    public Response login() {
        return new Response(200, "Login Successful");
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        String jwt = service.getToken(authenticationRequest);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(service.loadUserDetails(userDTO));
    }

    @PostMapping("/sendMail")
    public Response sendMail(@RequestBody UserDTO userDTO) {
        return service.getMail(userDTO);
    }

    @PutMapping("/changePassword")
    public Response changePassword(@RequestBody UserDTO userDTO, @RequestParam(value = "token") String token) {
        service.getId(token);
        service.changePassword(userDTO);
        return new Response(200, "Changed password successfully.");
    }
}
