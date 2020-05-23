package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.MessageResponse;
import com.bridgelabz.lmsapi.dto.UserDto;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;
    UserDao registerUser(UserDto userDTO);
    UserDao changePassword(UserDto userDTO);
    String getToken(AuthenticationRequest authenticationRequest) throws Exception;
    String getId(String token);
    MessageResponse getMail(UserDto userDTO);
    boolean checkUser(UserDto userDTO);

}
