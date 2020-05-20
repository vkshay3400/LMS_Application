package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long contactNumber;
    private String verified;
    private LocalDateTime creatorStamp;
    private String creatorUser;

}
