package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.UserDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.model.AuthenticationRequest;
import com.bridgelabz.lmsapi.dto.LoginDto;
import com.bridgelabz.lmsapi.model.UserDao;
import com.bridgelabz.lmsapi.repository.UserRepository;
import com.bridgelabz.lmsapi.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil util;

    /**
     * Method to load user by name
     *
     * @param userName
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserDao user = userRepository.findByFirstName(userName)
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.USER_NOT_FOUND, "User not found"));
        return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(),
                new ArrayList<>());
    }

    /**
     * Method to register user details
     *
     * @param userDTO
     * @return
     */
    @Override
    public UserDao registerUser(UserDto userDTO) {
        UserDao userDao = modelMapper.map(userDTO, UserDao.class);
        userDao.setCreatorStamp(LocalDateTime.now());
        userRepository.save(userDao);
        return userDao;
    }

    /**
     * Method to change password
     *
     * @param userDTO
     * @param token
     * @return
     */
    @Override
    public UserDao changePassword(UserDto userDTO, String token) {
        String id = util.extractUserName(token);
        UserDao userDao = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        userDao.setPassword(userDTO.getPassword());
        return userRepository.save(userDao);
    }

    /**
     * Method to get token
     *
     * @param authenticationRequest
     * @return
     */
    @Override
    public String getToken(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
        } catch (LMSException e) {
            throw new LMSException(LMSException.exceptionType.INVALID_PASSWORD, e.getMessage());
        }
        final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = util.generateToken(userDetails);
        return jwt;
    }

    /**
     * Method to send mail on the user's mail
     *
     * @param userDTO
     * @return
     */
    @Override
    public String sendMail(UserDto userDTO) {
        userDTO.setEmail(userDTO.getEmail());
        userDTO.setFirstName(userDTO.getFirstName());
        userDTO.setLastName(userDTO.getLastName());

        try {
            UserDao user = userRepository.findByEmail(userDTO.getEmail())
                    .orElseThrow(() -> new LMSException(LMSException.exceptionType.USER_NOT_FOUND, "User not found"));
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userDTO.getEmail());
            mail.setFrom("${gmail.username}");
            mail.setSubject("Regarding reset password ");
            mail.setText("Hello " + userDTO.getFirstName() + " please reset your password using the link and token " +
                    "Link: http://localhost:8080/changepassword " + "Use your email and a new password and use the token " +
                    "token: " + util.getToken(user.getId()));

            javaMailSender.send(mail);
            return new String("Mail sent successfully");
        } catch (LMSException e) {
            throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Method to check user
     *
     * @param loginDTO
     * @return
     */
    @Override
    public boolean checkUser(LoginDto loginDTO) {
        UserDao userDao = userRepository.findByEmail(loginDTO.email).orElseThrow(() -> new LMSException(LMSException.exceptionType
                .INVALID_EMAIL_ID, "User not found with email"));
        if (loginDTO.password.matches(userDao.getPassword()))
            return true;
        throw new LMSException(LMSException.exceptionType.USER_NOT_FOUND, "User not found");
    }

}
