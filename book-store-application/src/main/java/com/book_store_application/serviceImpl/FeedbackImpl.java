package com.book_store_application.serviceImpl;

import com.book_store_application.model.Book;
import com.book_store_application.model.Feedback;
import com.book_store_application.model.User;
import com.book_store_application.respository.BookRepository;
import com.book_store_application.respository.FeedbackRepository;
import com.book_store_application.respository.UserRepository;
import com.book_store_application.service.BookService;
import com.book_store_application.service.FeedbackService;
import com.book_store_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;
@Override
    public void submitFeedback(Long userId, Integer bookId, int rating, String review) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        Book book= bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("book not found"));
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setBook(book);
        feedback.setRating(rating);
        feedback.setReview(review);
        feedbackRepository.save(feedback);
    }
@Override
    public List<Feedback> getBookFeedback(Integer bookId) {
        Book book= bookRepository.findById(bookId).orElseThrow(()->new RuntimeException("book not found"));
        return feedbackRepository.findByBook(book);
    }
}