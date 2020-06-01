package com.bridgelabz.lmsapi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "Model for User Details")
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
    private LocalDateTime creatorStamp;
    private long creatorUser;

}
