package com.javafruit.AuthenticationService.model;


import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequest {
    private String userId;
    private String userName;
    private String password;
    private  String roles;
    private String mobileNumber;
    private String emailId;
}
