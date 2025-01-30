package com.book_store_application.respository;

import com.book_store_application.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Boolean existsByBookNameIgnoreCase(String bookName);
    List<Book> findByBookNameContainingIgnoreCase(String bookName);
   // List<Book> findByUser_Id(Long userId);
}


