package com.book_store_application.serviceImpl;

import com.book_store_application.model.Image;
import com.book_store_application.respository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;
    public Image addImage(MultipartFile image) throws  IOException {
        Image imageData = new Image();
        imageData.setImageName(image.getOriginalFilename());
        imageData.setImageType(image.getContentType());
        imageData.setImageData(image.getBytes());
        System.out.println(imageData.getImageName());

        return imageRepository.save(imageData);
    }
}