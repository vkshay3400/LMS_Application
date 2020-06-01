package com.bridgelabz.lmsapi.dto;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class UploadDocumentsDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long candidateId;
    private String documentType;
    private String documentPath;
    private String status;
    private LocalDateTime creatorStamp;
    private long creatorUser;

}
