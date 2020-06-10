package com.bridgelabz.lmsapi.util;

import com.bridgelabz.lmsapi.dto.MailDto;

public interface RabbitMq {

    void sendMessageToQueue(MailDto mailDto);

    void sendMail(MailDto mailDto);

}