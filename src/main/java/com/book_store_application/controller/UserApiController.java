package com.book_store_application.controller;

import com.book_store_application.requestdto.UserRequestDto;
import com.book_store_application.filter.JwtService;
import com.book_store_application.responsedto.UserResponseDto;
import com.book_store_application.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceImpl userService;
    @GetMapping
    public String message() {
        return "Hello";
    }



    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> userAccess() {
        return ResponseEntity.ok("This endpoint is accessible by USER and ADMIN.");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> adminAccess(@RequestAttribute("role") String role) {
        return ResponseEntity.ok("This endpoint is accessible only by ADMIN.");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{email}")
    public ResponseEntity<String> updateUser(
            @PathVariable String email,
            @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto isUpdated = userService.updateUser(email, userRequestDto);
        if (isUpdated!=null) {
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.status(400).body("User not updated. The user may not exist or might be marked as deleted.");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{emailId}")
    public ResponseEntity<String> deleteUser( @PathVariable String emailId) {
        try {
            userService.deleteUser(emailId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping ("/get/{emailId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String emailId) {
  UserResponseDto   getUser =userService.getUserById(emailId);
            if(getUser!=null){
            return ResponseEntity.ok(getUser);
        } else {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
    }
}

