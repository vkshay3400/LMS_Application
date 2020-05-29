package com.bridgelabz.lmsapi.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "Model for Qualification Details")
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
    @NotNull(message = "Passing Year is required")
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
    @NotNull(message = "Creator User is required")
    private String creatorUser;

}
