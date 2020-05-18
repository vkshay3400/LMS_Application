package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.model.DAOUser;

public interface UserService {
    DAOUser loadUserDetails(UserDTO userDTO);

    DAOUser changePassword(UserDTO userDTO);
}
