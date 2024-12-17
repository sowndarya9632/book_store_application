package com.book_store_application.controller;

import com.book_store_application.requestdto.WishlistRequestDto;
import com.book_store_application.responsedto.WishlistResponseDto;
import com.book_store_application.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<Void> addBookToWishlist(@RequestBody WishlistRequestDto requestDto) {
        wishlistService.addBookToWishlist(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistResponseDto>> getUserWishlist(@PathVariable Long userId) {
        List<WishlistResponseDto> wishlist = wishlistService.getUserWishlist(userId);
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeBookFromWishlist(@RequestBody WishlistRequestDto requestDto) {
        wishlistService.removeBookFromWishlist(requestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
