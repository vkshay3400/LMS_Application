package com.bridgelabz.lmsapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "bank_details")
@Entity(name = "bank_details")
public class BankDetailsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
