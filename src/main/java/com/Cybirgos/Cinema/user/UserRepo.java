package com.Cybirgos.Cinema.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByUsername (String username);
    Optional<User> findByEmail (String email);


}
