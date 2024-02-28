package com.javafruit.AuthenticationService.service;

import com.javafruit.AuthenticationService.entity.User_Details;
import com.javafruit.AuthenticationService.exception.UserNotExistException;
import com.javafruit.AuthenticationService.repository.UserRepository;
import com.javafruit.AuthenticationService.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    final private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("in the loadUserByName");
        Optional<User_Details> byUserName = userRepository.findByUserName(userName);

        byUserName.orElseThrow(() -> new UserNotExistException("User Doesn't exist in the system. Please register. Or contact with customer support."));

        return byUserName.stream().map(CustomUserDetails::new).findFirst().get();
    }
}
