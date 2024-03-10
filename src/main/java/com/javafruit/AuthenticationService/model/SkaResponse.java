package com.javafruit.AuthenticationService.model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkaResponse {
    private String token ,message, requestedURI,dateAndTime;
    private String roles;

}
