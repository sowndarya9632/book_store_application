package com.book_store_application.respository;

import com.book_store_application.model.Book;
import com.book_store_application.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByBook(Book book);
}

