package com.bridgelabz.lmsapi.util;

import com.bridgelabz.lmsapi.dto.MailDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqImpl implements RabbitMq {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMessageToQueue(MailDto mailDto) {
        final String exchange = "QueueExchangeConn";
        final String routingKey = "RoutingKey";
        rabbitTemplate.convertAndSend(exchange, routingKey, mailDto);

    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void sendMail(MailDto email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setFrom(email.getFrom());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        javaMailSender.send(message);
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void messageReceiver(MailDto email) {
        sendMail(email);
    }
}
