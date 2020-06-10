package com.bridgelabz.lmsapi.util;

import com.bridgelabz.lmsapi.dto.MailDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class RabbitMqImpl implements RabbitMq {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMessageToQueue(MailDto mailDto) {
        rabbitTemplate.convertAndSend(System.getenv().get("spring.rabbitmq.template.exchange"),
                System.getenv().get("spring.rabbitmq.template.routing-key"), mailDto);
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void sendMail(MailDto mailDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = null;
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(mailDto.getTo());
            helper.setText(mailDto.getBody(), true);
            helper.setSubject(mailDto.getSubject());
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}