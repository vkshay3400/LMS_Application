package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class UserDto {

    @NotNull
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private long contactNumber;
    @NotNull
    private String verified;
    @NotNull
    private LocalDateTime creatorStamp;
    @NotNull
    private String creatorUser;

}
