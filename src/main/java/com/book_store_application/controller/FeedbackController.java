package com.book_store_application.controller;
import com.book_store_application.model.Feedback;
import com.book_store_application.service.FeedbackService;
import com.book_store_application.serviceImpl.FeedbackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackImpl feedbackimpl;

    @PostMapping("/submit")
    public ResponseEntity<String> submitFeedback(
            @RequestParam Long userId,
            @RequestParam Integer bookId,
            @RequestParam int rating,
            @RequestParam String review) {
        try {
            feedbackimpl.submitFeedback(userId, bookId, rating, review);
            return ResponseEntity.ok("Feedback submitted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    // Endpoint to get all feedback for a specific book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Feedback>> getBookFeedback(@PathVariable Integer bookId) {
        try {
            List<Feedback> feedbackList = feedbackimpl.getBookFeedback(bookId);
            return ResponseEntity.ok(feedbackList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}

