package com.book_store_application.service;

import com.book_store_application.exception.ResourceNotFoundException;
import com.book_store_application.model.Book;
import com.book_store_application.requestdto.BookRequestDto;
import com.book_store_application.responsedto.BookResponseDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public interface BookService {
        BookResponseDto addBook(BookRequestDto bookRequestDto);
        BookResponseDto updateBookDetails( Integer id,BookRequestDto bookRequestDto) throws IOException, ResourceNotFoundException;
       List<BookResponseDto> getAllBooks();
        void removeBook(Integer id);
      List<BookResponseDto> searchBooks(@RequestParam String query);
        BookResponseDto changeBookQuantity(Integer id, Long quantity);
        BookResponseDto changeBookPrice(Integer id,double price);
    }


