package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PersonalDetailsDto {

    private Date birthDate;
    private String isBirthDateVerified;
    private String parentName;
    private String parentOccupation;
    private long parentMobileNumber;
    private long parentAnnualSalary;
    private String localAddress;
    private String permanentAddress;
    private String photoPath;
    private Date joiningDate;
    private String candidateStatus;
    private String remark;

}
