package com.Cybirgos.Cinema.profile;

import com.Cybirgos.Cinema.config.JwtService;
import com.Cybirgos.Cinema.images.Image;
import com.Cybirgos.Cinema.images.ImageRepo;
import com.Cybirgos.Cinema.images.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    @Autowired
    ProfileRepo profileRepo;
    @Autowired
    JwtService jwtService;
    @Autowired
    ImageRepo imageRepo;



    public ResponseEntity<Profile> getUserDetails(HttpServletRequest request) {
            try {
                var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
                var username = jwtService.extractUsername(token);
                var user = profileRepo.findByUsername(username).orElseThrow();
                return new ResponseEntity<>(user, HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
            }
        return new ResponseEntity<>(new Profile(), HttpStatus.OK);
         }


    public ResponseEntity<?> changeProfilePicture(MultipartFile file, HttpServletRequest request) throws IOException {
        Image savedImage = imageRepo.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        var username = jwtService.extractUsername(token);
        var profile = profileRepo.findByUsername(username).orElseThrow();
        profile.setProfilePicture(savedImage);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }

    public byte[] getImage(Long id){
        Optional<Image> dbImageData = imageRepo.findById(id);
        return ImageUtils.decompressImage(dbImageData.get().getImageData());
    }
}
