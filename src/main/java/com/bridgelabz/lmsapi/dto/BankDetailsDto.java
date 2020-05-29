package com.bridgelabz.lmsapi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "Model for Bank Details")
public class BankDetailsDto {

    @NotNull
    private long id;
    @NotNull(message = "Candidate Id is required")
    private long candidateId;
    @NotNull
    private String name;
    @NotNull
    private long accountNumber;
    @NotNull
    private String isAccountNumberVerified;
    @NotNull
    private long ifscCode;
    @NotNull
    private String isIfscCodeVerified;
    @NotNull
    private long panNumber;
    @NotNull
    private String isPanNumberVerified;
    @NotNull
    private long addhaarNumber;
    @NotNull
    private String isAddhaarNumVerified;
    @NotNull
    private LocalDateTime creatorStamp;
    @NotNull
    private String creatorUser;

}
