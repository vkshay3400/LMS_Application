package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDto {

    @NotEmpty
    public String email;
    @NotEmpty
    public String password;

}
