package com.book_store_application.controller;

import com.book_store_application.requestdto.BookRequestDto;
import com.book_store_application.responsedto.BookResponseDto;
import com.book_store_application.serviceImpl.BookServiceImpl;
import com.book_store_application.utility.TokenUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/books")
public class BookApiController {
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    private BookServiceImpl bookServiceimpl;

    @GetMapping
    public String message() {
        return "Hello";
    }
    @PostMapping("/addbook")
    public ResponseEntity<BookResponseDto> addBook( @RequestAttribute String role,@ModelAttribute @Valid BookRequestDto bookRequestDto){
        if(role==null) {
            throw new IllegalArgumentException("Users and admin not found");
        }
        if(role.equalsIgnoreCase("ADMIN")) {
            return new ResponseEntity<>(bookServiceimpl.addBook(bookRequestDto),HttpStatus.CREATED);
        }
        else {
            throw new IllegalArgumentException("Users cannot add book,only admin can add");
        }
    }

    @PutMapping("/updatebook/{bookId}")
    public ResponseEntity<BookResponseDto> updateBookDetails(@RequestAttribute("role") String role,@PathVariable Integer bookId,@RequestBody BookRequestDto bookRequestBody) throws IOException {
        if(role == null || role.equalsIgnoreCase("User")) {
            throw new IllegalArgumentException("Users cannot update book, Only admin can update book.");
        }
        return new ResponseEntity<>(bookServiceimpl.updateBookDetails(bookId,bookRequestBody), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> removeBookById(@RequestAttribute("role") String role, @PathVariable Integer bookId) {
        if("ADMIN".equalsIgnoreCase(role)) {
            bookServiceimpl.removeBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            throw new IllegalArgumentException("Users cannot remove book");
        }
    }

    @PutMapping("/change_by_quantity/{quantity}")
    public ResponseEntity<BookResponseDto> changeBookQuantity(@RequestAttribute("role") String role,@PathVariable Integer bookId,@PathVariable Long quantity ) {
        if("ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(bookServiceimpl.changeBookQuantity(bookId, quantity), HttpStatus.ACCEPTED);
        }
        else {
            throw new IllegalArgumentException("Users cannot change book quantity");
        }
    }
    @PutMapping("/change_by_price/{price}")
    public ResponseEntity<BookResponseDto> changeBookPrice(@RequestAttribute("role") String role,@PathVariable Integer bookId,@RequestParam double price ) {
        if("ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(bookServiceimpl.changeBookQuantity(bookId, (long) price), HttpStatus.ACCEPTED);
        }
        else {
            throw new IllegalArgumentException("Users cannot change book quantity");
        }
    }



}
