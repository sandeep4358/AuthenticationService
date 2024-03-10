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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

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

        log.info("enter in the get token method.:: {}",authRequest);
        Authentication authenticate = null;

        try {
            authenticate = authenticate(authRequest.getUserName(), authRequest.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }

        //Once the user is authenticated then the token will be generated.


        AuthResponseBody responseBody = new AuthResponseBody();
        authenticate.getAuthorities().stream().forEach(g->{
            log.info("granted authority :: {}",g.getAuthority());
        });


        if (authenticate.isAuthenticated()) {
            log.info("##########################################################1");
            responseBody.setStatus("00");
            SkaResponse response  =  new SkaResponse();
            response.setMessage("Token Generated Successfully.");
            String tokens=userService.generateToken(authenticate);
            response.setToken(tokens);
            response.setRequestedURI(request.getRequestURI());
            response.setDateAndTime(CommonUtility.getCurrentDateTime());
            response.setRoles(userService.getRoles(tokens));
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
            throw new DisabledException("User is not active. Please contact the customer support.", e);
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            throw new UserNotExistException("Please enter the correct credential");
        }catch (Exception e){
            throw new UserNotExistException("Please enter the correct credential");

        }
    }

}
