package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CandidateQualificationDto {

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
