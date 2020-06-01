package com.bridgelabz.lmsapi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "fellowship")
@Entity(name = "fellowship")
public class FellowshipDao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private LocalDateTime creatorStamp;
    private long creatorUser;
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

    public LocalDateTime getCreatorStamp() {
        return creatorStamp;
    }

    public void setCreatorStamp(LocalDateTime creatorStamp) {
        this.creatorStamp = LocalDateTime.now();
    }

    public long getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(long creatorUser) {
        this.creatorUser = this.id;
    }
}
