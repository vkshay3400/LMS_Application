package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CandidateQualificationDto {

    @NotNull
    private long id;
    @NotNull(message = "Candidate Id is required")
    private long candidateId;
    private String diploma;
    private String degreeName;
    private String isDegreeNameVerified;
    private String employeeDiscipline;
    private String isEmployeeDisciplineVerified;
    @NotNull
    private long passingYear;
    @NotNull
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
    @NotNull
    private LocalDateTime creatorStamp;
    @NotNull
    private String creatorUser;

}
