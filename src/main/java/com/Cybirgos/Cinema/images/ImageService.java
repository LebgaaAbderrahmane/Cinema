package com.Cybirgos.Cinema.images;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    public String uploadImage(MultipartFile file) throws IOException {

        Image imageData = imageRepo.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    public byte[] getImage(String fileName){
        Optional<Image> dbImageData = imageRepo.findByName(fileName);
        return ImageUtils.decompressImage(dbImageData.get().getImageData());
    }
}
