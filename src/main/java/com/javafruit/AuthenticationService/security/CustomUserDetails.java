package com.javafruit.AuthenticationService.security;

import com.javafruit.AuthenticationService.entity.User_Details;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author ska software
 */
public class CustomUserDetails implements UserDetails {

    private String userId;
    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;
    private String mobileNumber;
    private String emailId;

    private boolean isActive;

    private boolean isExpired;

    public CustomUserDetails(User_Details userDetails) {

        this.userName = userDetails.getUserName();
        this.password = userDetails.getPassword();

        authorities = Arrays.asList(userDetails.getRoles()
                .split(",")).stream()
                .map((role) -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toCollection(ArrayList::new));
        this.isExpired= userDetails.isExpired();
        this.isActive = userDetails.isActive();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
