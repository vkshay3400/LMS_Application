package com.bridgelabz.lmsapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "user")
@Entity(name = "user")
public class DAOUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private long contact_number;
    private String verified;
    private LocalDateTime creator_stamp;
    private String creator_user;

}
