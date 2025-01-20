package com.book_store_application.controller;
import com.book_store_application.requestdto.BookRequestDto;
import com.book_store_application.responsedto.BookResponseDto;
import com.book_store_application.service.BookService;
import com.book_store_application.serviceImpl.BookServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/filter")
public class BookController {
    @Autowired
    private BookServiceImpl bookServiceImpl;

    @PostMapping("/addbook")
    public ResponseEntity<BookResponseDto> addBook(@ModelAttribute @Valid BookRequestDto bookRequestDto) throws IOException {
        BookResponseDto bookResponseDto=  bookServiceImpl.addBook(bookRequestDto);
      return ResponseEntity.ok(bookResponseDto);

    }

   /* @PutMapping("/updatebook")
    public ResponseEntity<String> updateBookDetails(@RequestAttribute("role") String role,
                                                    @RequestParam("bookName") String bookName,
                                                    @RequestParam("bookAuthor") String bookAuthor,
                                                    @RequestParam("bookDescription") String bookDescription,
                                                    @RequestParam("bookLogo") MultipartFile bookLogo) throws IOException {
        if(role == null || role.equalsIgnoreCase("User")) {
            throw new IllegalArgumentException("Users cannot update book, Only admin can update book.");
        }
        return new ResponseEntity<>(bookService.updateBookDetails(bookName, bookAuthor, bookDescription, bookLogo), HttpStatus.OK);
    }

    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity<String> removeBookById(@RequestAttribute("role") String role, @PathVariable Long id) {
        if("ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(bookService.removeBook(id), HttpStatus.ACCEPTED);
        }
        else {
            throw new IllegalArgumentException("Users cannot remove book");
        }
    }

    @PutMapping("/changequantity")
    public ResponseEntity<String> changeBookQuantity(@RequestAttribute("role") String role, @RequestParam Long bookId, @RequestParam Long quantity) {
        if("ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(bookService.changeBookQuantity(bookId, quantity), HttpStatus.ACCEPTED);
        }
        else {
            throw new IllegalArgumentException("Users cannot change book quantity");
        }
    }*/

}
