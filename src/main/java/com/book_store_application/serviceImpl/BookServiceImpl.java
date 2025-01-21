package com.book_store_application.serviceImpl;

import com.book_store_application.exception.ResourceNotFoundException;
import com.book_store_application.model.Book;
import com.book_store_application.requestdto.BookRequestDto;
import com.book_store_application.responsedto.BookResponseDto;
import com.book_store_application.respository.BookRepository;
import com.book_store_application.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:3000") // Allow React app to access
@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookResponseDto addBook(MultipartFile bookImage,BookRequestDto bookRequestDto) {
        Boolean isPresent = bookRepository.existsByBookNameIgnoreCase(bookRequestDto.getBookName());
        if (isPresent) {
            throw new IllegalArgumentException("Book already exists");
        }
        Book book =new Book();
        book.setId(book.getId());
        book.setBookName(bookRequestDto.getBookName());
        book.setAuthorName(bookRequestDto.getAuthorName());
        book.setImages(bookRequestDto.getImage());
        book.setPrice(bookRequestDto.getPrice());
        book.setDescription(bookRequestDto.getDescription());
        book.setQuantity(bookRequestDto.getQuantity());
        return mapToDto( bookRepository.save(book));
    }

    @Override
    public BookResponseDto updateBookDetails(Integer id, BookRequestDto bookRequestDto) throws IOException, ResourceNotFoundException {
        Optional<Book> existingBook = bookRepository.findById(id);
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setImages(bookRequestDto.getImage());
            book.setAuthorName(bookRequestDto.getAuthorName());
            book.setBookName(bookRequestDto.getBookName());
            book.setDescription(bookRequestDto.getDescription());
            book.setPrice(bookRequestDto.getPrice());
            book.setQuantity(bookRequestDto.getQuantity());
            Book updatedBook = bookRepository.save(book);
            return mapToDto(updatedBook);
        }
        throw new ResourceNotFoundException("Book not found with id: " + id);
    }

    @Override
    public List<BookResponseDto> getAllBooks() {
        List<Book> books=bookRepository.findAll();
        return books.stream()
                .map(book -> new BookResponseDto(book.getId(), book.getBookName(), book.getAuthorName(), book.getDescription(),book.getImages(),book.getPrice(),book.getQuantity()))
                .collect(Collectors.toList());
    }


    @Override
    public void removeBook(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }

    }

    @Override
    public BookResponseDto changeBookQuantity(Integer id, Long quantity) {
     Optional<Book> book=  bookRepository.findById(id);
     if(book.isPresent()){
     Book book1   = book.get();
     book1.setQuantity(quantity);
     return mapToDto(  bookRepository.save(book1));
     }
    throw new ResourceNotFoundException("book id not found");
    }

    @Override
    public BookResponseDto changeBookPrice(Integer id, double price) {
        Optional<Book> book=  bookRepository.findById(id);
        if(book.isPresent()){
            Book book1   = book.get();
            book1.setPrice(price);
            return mapToDto(  bookRepository.save(book1));
        }
        throw new ResourceNotFoundException("book id not found");
    }



    private BookResponseDto mapToDto(Book book) {
        BookResponseDto bookResponseDto=new BookResponseDto();
        bookResponseDto.setId(book.getId());
        bookResponseDto.setAuthorName(book.getAuthorName());
        bookResponseDto.setBookName(book.getBookName());
        bookResponseDto.setDescription(book.getDescription());
        bookResponseDto.setPrice(book.getPrice());
        bookResponseDto.setQuantity(book.getQuantity());
        bookResponseDto.setImage(book.getImages());
        return bookResponseDto;
    }
    @Override
    public List<BookResponseDto> searchBooks(@RequestParam String query) {
        List<Book> books = bookRepository.findByBookNameContainingIgnoreCase(query);
        return books.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Override
    public BookResponseDto addBook(BookRequestDto bookRequestDto) {
        Boolean isPresent = bookRepository.existsByBookNameIgnoreCase(bookRequestDto.getBookName());
        if (isPresent) {
            throw new IllegalArgumentException("Book already exists");
        }
        Book book =new Book();
        book.setId(book.getId());
        book.setBookName(bookRequestDto.getBookName());
        book.setAuthorName(bookRequestDto.getAuthorName());
        book.setImages(bookRequestDto.getImage());
        book.setPrice(bookRequestDto.getPrice());
        book.setDescription(bookRequestDto.getDescription());
        book.setQuantity(bookRequestDto.getQuantity());
        return mapToDto( bookRepository.save(book));
    }


}

