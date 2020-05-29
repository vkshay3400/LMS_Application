package com.bridgelabz.lmsapi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description = "Model for Personal Details")
public class PersonalDetailsDto {

    @NotNull
    private Date birthDate;
    @NotNull
    private String isBirthDateVerified;
    @NotNull
    private String parentName;
    @NotNull
    private String parentOccupation;
    @NotNull
    private long parentMobileNumber;
    @NotNull
    private long parentAnnualSalary;
    @NotNull
    private String localAddress;
    @NotNull
    private String permanentAddress;
    private String photoPath;
    @NotNull
    private Date joiningDate;
    private String candidateStatus;
    private String remark;

}
