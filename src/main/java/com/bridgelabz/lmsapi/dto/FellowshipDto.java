package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class FellowshipDto {

    @NotNull
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String middleName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String hiredCity;
    @NotNull
    private String degree;
    @NotNull
    private Date hiredDate;
    @NotNull
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
    @NotNull
    private Date joiningDate;
    @NotNull
    private String candidateStatus;
    @NotNull
    private String remark;

}
