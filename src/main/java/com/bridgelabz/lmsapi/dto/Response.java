package com.bridgelabz.lmsapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    int status;
    String message;
}
