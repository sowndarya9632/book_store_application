package com.book_store_application.controller;

import com.book_store_application.requestdto.WishlistRequestDto;
import com.book_store_application.responsedto.SuccessResponse;
import com.book_store_application.responsedto.WishlistResponseDto;
import com.book_store_application.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addBookToWishlist(@RequestBody WishlistRequestDto requestDto) {
        wishlistService.addBookToWishlist(requestDto);

        // Create the response object
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book added to wishlist successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistResponseDto>> getUserWishlist(@PathVariable Long userId) {
        List<WishlistResponseDto> wishlist = wishlistService.getUserWishlist(userId);
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, String>> removeBookFromWishlist(@RequestBody WishlistRequestDto requestDto) {
        wishlistService.removeBookFromWishlist(requestDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book remove successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
