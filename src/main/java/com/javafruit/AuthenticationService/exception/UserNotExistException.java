package com.javafruit.AuthenticationService.exception;
public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String msg){
        super(msg);
    }
}
