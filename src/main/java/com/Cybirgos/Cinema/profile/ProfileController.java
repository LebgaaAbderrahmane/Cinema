package com.Cybirgos.Cinema.profile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/profile")
@CrossOrigin
@RequiredArgsConstructor
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @GetMapping("userDetails") //add score to profile
    public ResponseEntity<Profile> getUserDetails(HttpServletRequest request){
        return profileService.getUserDetails(request);
    }
}
