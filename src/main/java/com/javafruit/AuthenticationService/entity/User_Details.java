package com.javafruit.AuthenticationService.entity;

import javax.persistence.*;
import lombok.*;


@Entity
@Table(name = "USER_DETAILS")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User_Details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String userName;
    private String password;
    private String roles;
    private String mobileNumber;
    private String emailId;

    private boolean isActive;

    private boolean isExpired;
}
