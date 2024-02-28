package com.javafruit.AuthenticationService.exception;

import com.javafruit.AuthenticationService.model.AuthResponseBody;
import com.javafruit.AuthenticationService.model.SkaResponse;
import com.javafruit.AuthenticationService.utility.CommonUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<AuthResponseBody> handleUserNotExistException(final UserNotExistException exception,final HttpServletRequest request){
      log.error("************1*************************");
        return getResponse(exception,request,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserInvalidCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<AuthResponseBody> handleUserInvalidCredentialsException(final UserInvalidCredentialsException exception,final HttpServletRequest request){
        log.error("************2*************************");
        return getResponse(exception,request,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<AuthResponseBody> handleBadCredentialsException(final BadCredentialsException exception,final HttpServletRequest request){
        log.error("************4*************************");
        return getResponse(exception,request,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<AuthResponseBody> globalExceptionHandler(final Exception exception,final HttpServletRequest request){
        log.error("************3*************************");
       // exception.printStackTrace();
        return getResponse(exception,request,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity getResponse(final Exception exception, final HttpServletRequest request, HttpStatus statusCode){



        String strDate = CommonUtility.getCurrentDateTime();
        SkaResponse error = new SkaResponse();

        if(exception.getMessage().contains("because \"authenticate\" is null"))
            error.setMessage("INVALID_CREDENTIALS : Please enter the correct credential");
        else
            error.setMessage(exception.getMessage());


        error.setRequestedURI(request.getRequestURI());
        error.setDateAndTime(strDate);
        //by default the status code send will be "HttpStatus.EXPECTATION_FAILED"
        if(statusCode==null){
            statusCode = HttpStatus.EXPECTATION_FAILED;
        }



        AuthResponseBody responseBody =  new AuthResponseBody();
        responseBody.setData(error);
        responseBody.setStatus("01");
        return  ResponseEntity.status(statusCode).body(responseBody);
    }
}
