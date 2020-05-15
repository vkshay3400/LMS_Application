package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.service.Notification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private Notification notification;

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
}
