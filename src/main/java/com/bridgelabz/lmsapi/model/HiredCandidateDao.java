package com.bridgelabz.lmsapi.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "hired_candidate")
@Entity(name = "hired_candidate")
public class HiredCandidateDao implements Serializable{

    @Id
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
    private String status;
    private LocalDateTime creatorStamp;
    private long creatorUser;

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
