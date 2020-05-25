package com.bridgelabz.lmsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDto {
    private Date timestamp;
    private String message;
    private String details;
}
