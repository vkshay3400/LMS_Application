package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.AuthenticationResponse;
import com.bridgelabz.lmsapi.model.DAOUser;
import com.bridgelabz.lmsapi.repository.UserRepository;
import com.bridgelabz.lmsapi.service.UserServiceImpl;
import com.bridgelabz.lmsapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil util;

    @RequestMapping("/login")
    public String login() {
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
        final String jwt = util.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(userDetailsService.loadUserDetails(userDTO));
    }

    @PostMapping("/send-mail")
    public String sendMail(@RequestBody UserDTO userDTO) {
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setFirst_name(userDTO.getFirst_name());
        userDTO.setLast_name(userDTO.getLast_name());

        try {
            DAOUser user = userRepository.findByEmail(userDTO.getEmail());
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userDTO.getEmail());
            mail.setFrom("${gmail.username}");
            mail.setSubject("Regarding reset password ");
            mail.setText("Hello " + userDTO.getFirst_name() + " please reset your password using the link and token " +
                    "Link: http://localhost:8080/change-password " +
                    "Use your email and a new password and use the token " +
                    "token: " + util.getToken(user.getId()));

            javaMailSender.send(mail);
        } catch (Exception e) {
            return ("Mail Exception");
        }
        return "Mail sent successfully.";
    }

    @PutMapping("/change-password")
    public String changePassword(@RequestBody UserDTO userDTO, @RequestParam(value = "token") String token) {
        String id = util.extractUserName(token);
        userDetailsService.changePassword(userDTO);
        return ("Changed password successfully.");
    }
}
