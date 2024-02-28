package com.javafruit.AuthenticationService.repository;

import com.javafruit.AuthenticationService.entity.User_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User_Details,Long> {
    public Optional<User_Details> findByUserId(String userId);
    public Optional<User_Details> findByUserName(String userName);


}
