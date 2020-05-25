package com.bridgelabz.lmsapi.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CheckLoginDto {
    @NotEmpty
    public String email;
    @NotEmpty
    public String password;

}
