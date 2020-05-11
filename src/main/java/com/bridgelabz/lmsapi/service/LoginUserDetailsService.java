package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.model.DAOUser;
import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        DAOUser user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + userName + " not found ");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public DAOUser save(UserDTO userDTO) {
        DAOUser newUser = new DAOUser();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        return userRepository.save(newUser);
    }
}
