package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BankDetailsDto {

    private long id;
    private long candidateId;
    private String name;
    private long accountNumber;
    private String isAccountNumberVerified;
    private long ifscCode;
    private String isIfscCodeVerified;
    private long panNumber;
    private String isPanNumberVerified;
    private long addhaarNumber;
    private String isAddhaarNumVerified;
    private LocalDateTime creatorStamp;
    private String creatorUser;

}
