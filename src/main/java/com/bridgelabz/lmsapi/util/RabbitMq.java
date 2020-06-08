package com.bridgelabz.lmsapi.util;

import com.bridgelabz.lmsapi.dto.MailDto;

public interface RabbitMq {

    void sendMail(MailDto mailDto);
    void sendMessageToQueue(MailDto mailDto);
    void messageReceiver(MailDto mailDto);

}