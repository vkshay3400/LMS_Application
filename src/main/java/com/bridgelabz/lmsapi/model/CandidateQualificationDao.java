package com.bridgelabz.lmsapi.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "candidate_qualification")
@Entity(name = "candidate_qualification")
public class CandidateQualificationDao implements Serializable{

    @Id
    private long id;
    private long candidateId;
    private String diploma;
    private String degreeName;
    private String isDegreeNameVerified;
    private String employeeDiscipline;
    private String isEmployeeDisciplineVerified;
    private long passingYear;
    private String isPassingYearVerified;
    private long aggregatePercentage;
    private long finalYearPercentage;
    private String isFinalYearPercentageVerified;
    private String trainingInstitute;
    private String isTrainingInstituteVerified;
    private long trainingDurationMonth;
    private String isTrainingDurationMonthVerified;
    private String isOtherTrainingVerified;
    private String otherTraining;
    private LocalDateTime creatorStamp;
    private String creatorUser;
}
