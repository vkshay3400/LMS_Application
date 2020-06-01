package com.bridgelabz.lmsapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
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
    private long creatorUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidateId", referencedColumnName = "id", insertable = false, updatable = false)
    private FellowshipDao fellowshipDao;

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
        this.creatorUser = this.candidateId;
    }

}
