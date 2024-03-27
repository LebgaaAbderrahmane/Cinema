package com.Cybirgos.Cinema.profile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/profile")
@CrossOrigin
@RequiredArgsConstructor
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @GetMapping("userDetails")
    public ResponseEntity<Profile> getUserDetails(HttpServletRequest request){
        return profileService.getUserDetails(request);
    }
//    @PostMapping(value = "/changeProfilePicture",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> changeProfilePicture (@RequestPart MultipartFile file,HttpServletRequest request) throws IOException {
//        return profileService.changeProfilePicture(file,request);
//    }
//    @GetMapping("getProfilePicture/{id}")
//    public ResponseEntity<?> getProfilePicture (@PathVariable Long id){
//        byte[] imageData=profileService.getImage(id);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(imageData);
//    }
}
