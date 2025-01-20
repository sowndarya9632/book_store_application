package com.book_store_application.controller;

import com.book_store_application.model.Image;
import com.book_store_application.serviceImpl.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<?> addImages(@RequestPart MultipartFile image) throws IOException {
        Image image1 = imageService.addImage(image);
        return new ResponseEntity<>(image1, HttpStatus.CREATED);
    }
}