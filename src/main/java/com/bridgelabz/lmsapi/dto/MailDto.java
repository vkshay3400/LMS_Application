package com.bridgelabz.lmsapi.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Component
@Data
public class MailDto implements Serializable {

    @NotNull
    private String to;
    @NotNull
    private String from;
    @NotNull
    private String subject;
    @NotNull
    private String body;

}