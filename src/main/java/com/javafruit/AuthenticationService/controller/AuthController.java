package com.javafruit.AuthenticationService.controller;

import com.javafruit.AuthenticationService.exception.UserInvalidCredentialsException;
import com.javafruit.AuthenticationService.exception.UserNotExistException;
import com.javafruit.AuthenticationService.model.AuthRequest;
import com.javafruit.AuthenticationService.model.AuthResponseBody;
import com.javafruit.AuthenticationService.model.SkaResponse;
import com.javafruit.AuthenticationService.service.UserService;
import com.javafruit.AuthenticationService.utility.CommonUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    final  private UserService userService;
    final private AuthenticationManager authenticationManager;

    final private HttpServletRequest request;
    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity registerUser(@RequestBody AuthRequest request){
      log.info("saved user :: {}",request);
        userService.saveUser(request);

        return ResponseEntity.ok("User register successfully");
    }

    @PostMapping("/accessToken")
    public ResponseEntity getToken(@RequestBody AuthRequest authRequest) {
        log.info("enter in the get token method.");
        Authentication authenticate = null;
        try {
            log.info("Password : userName :: "+authRequest.getPassword()+ " : "+authRequest.getUserName());
            authenticate = authenticate(authRequest.getUserName(), authRequest.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }

        //Once the user is authenticated then the token will be generated.


        AuthResponseBody responseBody = new AuthResponseBody();

        if (authenticate.isAuthenticated()) {
            log.info("##########################################################1");
            responseBody.setStatus("00");
            SkaResponse response  =  new SkaResponse();
            response.setMessage("Token Generated Successfully.");
            response.setToken(userService.generateToken(authRequest.getUserName()));
            response.setRequestedURI(request.getRequestURI());
            response.setDateAndTime(CommonUtility.getCurrentDateTime());
            responseBody.setData(response);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            log.info("##########################################################2");
            throw new UserInvalidCredentialsException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token,@RequestParam("userName") String userName) {
        log.info("enter in the validation.");
        userService.validateToken(token,userName);
        return "Token is valid";
    }


    private Authentication authenticate(String userName, String password) throws UserNotExistException,DisabledException {
        log.info("enter :: {} :: {}",userName,password);
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException e) {
            log.error(e.getMessage());
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new UserNotExistException("INVALID_CREDENTIALS : Please enter the correct credential");
        }catch (Exception e){
            throw new UserNotExistException("Other_exception : Please enter the correct credential");

        }
    }

}
