package com.Cybirgos.Cinema.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token,Integer> {
    @Query(value = "select * from Token t where t.user_id =:userId and(t.expired = false or t.revoked = false)",nativeQuery = true)
    List<Token> findAllValidTokensByUser (Integer userId);
    Optional<Token> findByToken (String token);
}
