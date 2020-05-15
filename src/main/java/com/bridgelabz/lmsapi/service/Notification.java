package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.UserDTO;
import com.bridgelabz.lmsapi.model.DAOUser;
import com.bridgelabz.lmsapi.repository.UserRepository;
import com.bridgelabz.lmsapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class Notification {

    private JavaMailSender javaMailSender;

    @Autowired
    JwtUtil util;
    private UserDetails userDetails;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public Notification(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendNotification(UserDTO userDTO) throws MailException {
        DAOUser user = userRepository.findByEmail(userDTO.getEmail());
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userDTO.getEmail());
        mail.setFrom("${gmail.username}");
        mail.setSubject("Regarding reset password ");
        mail.setText("Hello " + userDTO.getFirst_name() + " please reset your password using the link and token " +
                "Link: http://localhost:8080/change-password " +
                "Use your email and a new password and use the token " +
                "token: " + util.getToken(user.getId()));

        javaMailSender.send(mail);
    }
}
