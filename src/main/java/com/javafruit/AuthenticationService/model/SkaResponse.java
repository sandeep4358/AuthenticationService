package com.javafruit.AuthenticationService.model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkaResponse {
    private String user,token ,message, requestedURI,dateAndTime,roles;

}
