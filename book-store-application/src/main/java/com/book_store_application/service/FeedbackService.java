package com.book_store_application.service;

import com.book_store_application.model.Feedback;

import java.util.List;

public interface FeedbackService {
    void submitFeedback(Long userId, Integer bookId, int rating, String review);
    List<Feedback> getBookFeedback(Integer bookId);
}
