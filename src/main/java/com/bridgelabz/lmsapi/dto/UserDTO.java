package com.bridgelabz.lmsapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private long contact_number;
    private String verified;
    private LocalDateTime creator_stamp;
    private String creator_user;

}
