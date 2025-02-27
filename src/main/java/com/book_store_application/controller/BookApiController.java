package com.book_store_application.controller;

import com.book_store_application.model.Book;
import com.book_store_application.model.Image;
import com.book_store_application.requestdto.BookRequestDto;
import com.book_store_application.responsedto.BookResponseDto;
import com.book_store_application.serviceImpl.BookServiceImpl;
import com.book_store_application.filter.JwtService;
import com.book_store_application.serviceImpl.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/api/v1/books")
public class BookApiController {
    @Autowired
    JwtService jwtService;
    @Autowired
    private BookServiceImpl bookServiceimpl;

    @Autowired
  private ImageService imageService;

    @GetMapping
    public String message() {
        return "Hello";
    }
    @PreAuthorize("hasRole('USER') ")
    @PostMapping(value = "/addbook",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BookResponseDto> addBook( @RequestPart("bookImage") MultipartFile bookImage,
                                                    @RequestPart("bookRequest") String bookRequestJson) throws IOException {
        // Convert JSON string to BookRequest object
        ObjectMapper objectMapper = new ObjectMapper();
        BookRequestDto bookRequest = objectMapper.readValue(bookRequestJson, BookRequestDto.class);

        // Save the image (business logic handled by a service)
        Image savedImage = imageService.addImage(bookImage);
       bookRequest.setImage(savedImage);

        // Save the book
       BookResponseDto books= bookServiceimpl.addBook(bookRequest);
        if(books!=null) {
            return ResponseEntity.ok(books);
        }
        else {
            throw new IllegalArgumentException("cannot add book");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatebook/{bookId}")
    public ResponseEntity<BookResponseDto> updateBookDetails(@PathVariable Integer bookId,@RequestBody BookRequestDto bookRequestBody) throws IOException {
        BookResponseDto books = bookServiceimpl.updateBookDetails(bookId, bookRequestBody);
        if (books != null) {
            return ResponseEntity.ok(books);
        }
      else
        {
            throw new IllegalArgumentException("cannot add book");
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> removeBookById(@PathVariable Integer bookId) {
        try {
            bookServiceimpl.removeBook(bookId);
            return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cannot delete the book as it is referenced in other records (e.g., wishlist).");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }
    @CrossOrigin(origins = "http://localhost:3000") // Allow React app to access
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/search")
    public List<BookResponseDto> searchBooks(@RequestParam String query) {
        List<BookResponseDto> books = bookServiceimpl.searchBooks(query);
        return books.stream()
                .map(book -> new BookResponseDto(
                        book.getId(),
                        book.getBookName(),
                        book.getAuthorName(),
                        book.getPrice(),
                        book.getDescription(),
                        book.getQuantity(),
                        book.getImage())

                )
                .collect(Collectors.toList());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change_by_quantity/{quantity}")
    public ResponseEntity<BookResponseDto> changeBookQuantity(@PathVariable Integer bookId,@PathVariable Long quantity ) {
      BookResponseDto books=  bookServiceimpl.changeBookQuantity(bookId, quantity);
        if(books!=null) {
            return  ResponseEntity.ok(books);
        }
        else {
            throw new IllegalArgumentException("Users cannot change book quantity");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<BookResponseDto> books = bookServiceimpl.getAllBooks();
        if (books != null && !books.isEmpty()) {
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/change_by_price/{price}")
    public ResponseEntity<BookResponseDto> changeBookPrice(@PathVariable Integer bookId,@RequestParam double price ) {
        BookResponseDto books=  bookServiceimpl.changeBookPrice(bookId, price);
        if(books!=null) {
            return  ResponseEntity.ok(books);
        }
        else {
            throw new IllegalArgumentException("Users cannot change book price");
        }
    }
  /*  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<List<Book>> getBooksByUserId(@PathVariable Long userId) {
        List<Book> books = bookServiceimpl.getBooksByUserId(userId);

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(books);
    }*/

}
