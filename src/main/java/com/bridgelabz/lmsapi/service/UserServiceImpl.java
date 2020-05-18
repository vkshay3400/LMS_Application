package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.model.DAOUser;
import com.bridgelabz.lmsapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        DAOUser user = userRepository.findByFirst_name(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + userName + " not found ");
        }
        return new org.springframework.security.core.userdetails.User(user.getFirst_name(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public DAOUser loadUserDetails(UserDTO userDTO) {
        DAOUser daoUser = (DAOUser) modelMapper.map(userDTO, DAOUser.class);
        daoUser.setCreator_stamp(LocalDateTime.now());
        userRepository.save(daoUser);
        return daoUser;
    }

    @Override
    public DAOUser changePassword(UserDTO userDTO) {
        DAOUser daoUser = userRepository.findByEmail(userDTO.getEmail());
        daoUser.setPassword(userDTO.getPassword());
        return userRepository.save(daoUser);
    }
}

