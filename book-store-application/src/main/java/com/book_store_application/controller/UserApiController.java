package com.book_store_application.controller;

import com.book_store_application.requestdto.UserRequestDto;
import com.book_store_application.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    TokenUtility tokenUtility;

    @GetMapping
    public String message() {
        return "Hello";
    }


    @GetMapping("/user")
    public ResponseEntity<String> userAccess(@RequestAttribute("role") String role) {
        if ("USER".equals(role)) {
            return ResponseEntity.ok("User Content.");
        } else {
            return ResponseEntity.status(403).body("Access Denied");
        }
    }


    @GetMapping("/admin")
    public ResponseEntity<?> adminAccess(@RequestAttribute("role") String role) {

        if ("ADMIN".equals(role)) {
            return ResponseEntity.ok("Admin Content ");
        } else {
            return ResponseEntity.status(403).body("Access Denied");
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<String> updateUser(@RequestAttribute("role") String role,
                                             @PathVariable String email,
                                             @RequestBody UserRequestDto userRequestDto) {
        if ("ADMIN".equals(role)) {
            return ResponseEntity.ok("Admin Content ");
        } else {
            return ResponseEntity.status(403).body("Access Denied");
        }
    }

    @DeleteMapping("/delete/{emailId}")
    public ResponseEntity<String> deleteUser(@RequestAttribute("role") String role, @PathVariable String emailId) {
        if ("ADMIN".equals(role)) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(403).body("Access Denied"); // Forbidden
        }
    }

    @GetMapping ("/get/{email}")
    public ResponseEntity<String> getUser(@RequestAttribute("role") String role, @PathVariable String email) {
        if ("ADMIN".equals(role)) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(403).body("Access Denied"); // Forbidden
        }
    }
}

