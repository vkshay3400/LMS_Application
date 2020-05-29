package com.bridgelabz.lmsapi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "Model for Login Details")
public class LoginDto {

    @Pattern(regexp = ("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"))
    @NotEmpty(message = "Please enter valid email id")
    public String email;
    @NotEmpty(message = "Please enter valid password id")
    public String password;

}
