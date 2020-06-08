package com.bridgelabz.lmsapi.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class MailDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String to;
    private String from;
    private String subject;
    private String body;

}