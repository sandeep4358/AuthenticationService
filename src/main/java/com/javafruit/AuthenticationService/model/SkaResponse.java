package com.javafruit.AuthenticationService.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkaResponse {
    private String token ,message, requestedURI,dateAndTime ;

}
