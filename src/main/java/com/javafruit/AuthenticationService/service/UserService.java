package com.javafruit.AuthenticationService.service;

import com.javafruit.AuthenticationService.entity.User_Details;
import com.javafruit.AuthenticationService.model.AuthRequest;
import com.javafruit.AuthenticationService.repository.UserRepository;
import com.javafruit.AuthenticationService.utility.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;


    final private JwtTokenUtil jwtService;
    public boolean saveUser(AuthRequest requestCred) {
        log.info("{}"+requestCred);
        UUID userId=UUID.randomUUID(); //Generates random UUID

        User_Details user = User_Details.builder()
                .userName(requestCred.getUserName())
                .emailId(requestCred.getEmailId())
                .mobileNumber(requestCred.getMobileNumber())
                .password(requestCred.getPassword())
                .build();

        String userRole = String.join(",", Arrays.asList(requestCred.getRoles()));
        UserService.log.info("Assigned user roles are {}", userRole);
        user.setRoles(userRole);
        user.setActive(true);
        user.setExpired(false);
        user.setUserId(userId.toString());

        UserService.log.info("New User created details are {}",user);

        User_Details save = userRepository.save(user);
        return true;
    }
    public String generateToken(String username) {
        UserService.log.info("in the generate token service.");
        return jwtService.generateToken(username);
    }

    public void validateToken(String token,String username) {
        log.info("validateToken :: {}",token);
        jwtService.validateToken(token,username);
    }
}
