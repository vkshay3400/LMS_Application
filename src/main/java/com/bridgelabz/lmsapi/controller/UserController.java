package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.configuration.ApplicationConfig;
import com.bridgelabz.lmsapi.dto.ResponseDto;
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
@Api(tags = "User Details", value = "UserDetails", description = "Controller for User Details update")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * API for register user
     *
     * @param userDto
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDao> saveUser(@RequestBody UserDto userDto) {
        return new ResponseEntity(new ResponseDto(service.registerUser(userDto), ApplicationConfig
                .getMessageAccessor().getMessage("101")) ,HttpStatus.CREATED);
    }

    /**
     * API for authenticate user
     *
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/authenticate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> createAuthenticationToken(@RequestBody AuthenticationRequest
                                                                             authenticationRequest) throws Exception {
        String token = service.getToken(authenticationRequest);
        return new ResponseEntity<>(new ResponseDto(token, ApplicationConfig
                .getMessageAccessor().getMessage("102")), HttpStatus.CREATED);
    }

    /**
     * API for login user
     *
     * @param loginDto
     * @return
     */
    @GetMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDto> login(@RequestBody LoginDto loginDto) {
        boolean userId = service.checkUser(loginDto);
        return new ResponseEntity<>(new ResponseDto(userId,ApplicationConfig
                .getMessageAccessor().getMessage("103")), HttpStatus.OK);
    }

    /**
     * API to send mail
     *
     * @param userDTO
     * @return
     */
    @PostMapping(value = "/sendmail")
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<ResponseDto> sendMail(@RequestBody UserDto userDTO) {
        return new ResponseEntity<>(new ResponseDto(service.sendMail(userDTO), ApplicationConfig
                .getMessageAccessor().getMessage("104")), HttpStatus.GONE);
    }

    /**
     * API to change password
     *
     * @param userDto
     * @param token
     * @return
     */
    @PutMapping(value = "/changepassword")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> changePassword(@RequestBody UserDto userDto,
                                                      @RequestParam(value = "token") String token) {
        return new ResponseEntity<>(new ResponseDto(service.changePassword(userDto,token), ApplicationConfig
        .getMessageAccessor().getMessage("105")),HttpStatus.CREATED);
    }

}
