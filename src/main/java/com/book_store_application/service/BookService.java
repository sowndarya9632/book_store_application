package com.book_store_application.service;

import com.book_store_application.exception.ResourceNotFoundException;
import com.book_store_application.requestdto.BookRequestDto;
import com.book_store_application.responsedto.BookResponseDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
        BookResponseDto addBook(MultipartFile bookImage,BookRequestDto bookRequestDto);
        BookResponseDto updateBookDetails( Integer id,BookRequestDto bookRequestDto) throws IOException, ResourceNotFoundException;
       List<BookResponseDto> getAllBooks();
        void removeBook(Integer id);
      List<BookResponseDto> searchBooks(@RequestParam String query);
        BookResponseDto changeBookQuantity(Integer id, Long quantity);
        BookResponseDto changeBookPrice(Integer id,double price);
        BookResponseDto addBook(BookRequestDto bookRequestDto);
    }


