package com.Cybirgos.Cinema.profile;

import com.Cybirgos.Cinema.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    ProfileRepo profileRepo;
    @Autowired
    JwtService jwtService;




    public ResponseEntity<Profile> getUserDetails(HttpServletRequest request) {
            try {
                var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
                var username = jwtService.extractUsername(token);
                var user = profileRepo.findByUsername(username).orElseThrow();
                return new ResponseEntity<>(user, HttpStatus.OK);
            }catch (Exception ignored){
            }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
         }


}
