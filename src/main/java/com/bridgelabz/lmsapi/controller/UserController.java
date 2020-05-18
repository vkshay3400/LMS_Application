package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.AuthenticationResponse;
import com.bridgelabz.lmsapi.service.LoginUserDetailsService;
import com.bridgelabz.lmsapi.service.Notification;
import com.bridgelabz.lmsapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginUserDetailsService userDetailsService;

    @Autowired
    private Notification notification;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    LoginUserDetailsService service;

    @RequestMapping("/login")
    public String hello() {
        return "Login Successful";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(userDetailsService.loadUserDetails(userDTO));
    }

    @PostMapping("/send-mail")
    public String signUpSuccess(@RequestBody UserDTO userDTO) {
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setFirst_name(userDTO.getFirst_name());
        userDTO.setLast_name(userDTO.getLast_name());

        try {
            notification.sendNotification(userDTO);
        } catch (Exception e) {
            return ("Mail Exception");
        }
        return "Mail sent successfully.";
    }

    @PutMapping("/change-password")
    public String signUpSuccess(@RequestBody UserDTO userDTO, @RequestParam(value = "token") String token) {
        String id = jwtUtil.extractUserName(token);
        service.changePassword(userDTO);
        return ("Changed password successfully.");
    }
}
