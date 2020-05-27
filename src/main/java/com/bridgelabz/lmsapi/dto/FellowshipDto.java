package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FellowshipDto {

    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String hiredCity;
    private String degree;
    private Date hiredDate;
    private long mobileNumber;
    private long permanentPincode;
    private String hiredLab;
    private String attitude;
    private String communicationRemark;
    private String knowledgeRemark;
    private String aggregateRemark;
    private Date creatorStamp;
    private String creatorUser;
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
