package com.javafruit.AuthenticationService.exception;

public class UserInvalidCredentialsException extends RuntimeException{
    private static final long serialVersionUID = -470180507998010368L;

    public UserInvalidCredentialsException(){
    super();
    }
    public UserInvalidCredentialsException(String msg){
        super(msg);
    }
}
