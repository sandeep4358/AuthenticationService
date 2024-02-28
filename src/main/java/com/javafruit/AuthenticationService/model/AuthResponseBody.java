package com.javafruit.AuthenticationService.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthResponseBody {
    private String status;
    private Object data;

}
