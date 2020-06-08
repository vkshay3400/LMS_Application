package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.UserDto;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.dto.LoginDto;
import com.bridgelabz.lmsapi.model.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

public interface UserService {

    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

    UserDao registerUser(UserDto userDTO);

    UserDao changePassword(UserDto userDTO, String token);

    String getToken(AuthenticationRequest authenticationRequest) throws Exception;

    String sendMail(UserDto userDTO);

}
