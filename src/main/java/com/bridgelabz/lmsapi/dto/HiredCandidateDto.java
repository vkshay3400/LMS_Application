package com.bridgelabz.lmsapi.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Component
@Data
public class HiredCandidateDto {

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
    private String status;
    @NotNull
    private Date creatorStamp;
    @NotNull
    private String creatorUser;

}
