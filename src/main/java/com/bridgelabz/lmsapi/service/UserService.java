package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.Response;
import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.model.DAOUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;

    DAOUser loadUserDetails(UserDTO userDTO);

    DAOUser changePassword(UserDTO userDTO);

    String getToken(AuthenticationRequest authenticationRequest) throws Exception;

    String getId(String token);

    Response getMail(UserDTO userDTO);
}
