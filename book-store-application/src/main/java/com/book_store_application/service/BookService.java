package com.book_store_application.service;

import com.book_store_application.exception.ResourceNotFoundException;
import com.book_store_application.requestdto.BookRequestDto;
import com.book_store_application.responsedto.BookResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
        BookResponseDto addBook(BookRequestDto bookRequestDto);

        BookResponseDto updateBookDetails( Integer id,BookRequestDto bookRequestDto) throws IOException, ResourceNotFoundException;

        void removeBook(Integer id);

        BookResponseDto changeBookQuantity(Integer id, Long quantity);
        BookResponseDto changeBookPrice(Integer id,double price);
    }


