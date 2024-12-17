package com.book_store_application.respository;

import com.book_store_application.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    Boolean existsByBookNameIgnoreCase(String bookName);
}
