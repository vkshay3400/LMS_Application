package com.bridgelabz.lmsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {
    int status;
    String message;
}
