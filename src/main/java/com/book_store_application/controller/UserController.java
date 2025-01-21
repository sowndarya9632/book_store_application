package com.book_store_application.controller;

import com.book_store_application.model.User;
import com.book_store_application.requestdto.AuthenticationRequest;
import com.book_store_application.requestdto.ResetRequestDto;
import com.book_store_application.requestdto.UserRequestDto;
import com.book_store_application.responsedto.AuthenticationResponse;
import com.book_store_application.responsedto.TokenResponseDto;
import com.book_store_application.responsedto.UserResponseDto;
import com.book_store_application.serviceImpl.UserServiceImpl;
import com.book_store_application.filter.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody UserRequestDto request
    ) {
        return ResponseEntity.ok(userServiceImpl.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = userServiceImpl.authenticate(request);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/get")
    public ResponseEntity<UserResponseDto> getUserById(@RequestParam String emailId) {
        try {
            UserResponseDto userResponseDto = userServiceImpl.getUserById(emailId);
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestParam String email,
                                                      @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser =userServiceImpl.updateUser(email, userRequestDto);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String emailID) {
        try {
            userServiceImpl.deleteUser(emailID);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reset-password")
    public ResponseEntity<UserResponseDto> resetPassword(@RequestParam String emailId,
                                                         @Valid @RequestBody ResetRequestDto resetRequestDto) {
        try {
            UserResponseDto updatedUser = userServiceImpl.resetPassword(emailId, resetRequestDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<UserResponseDto> forgetPassword(@Valid @RequestBody UserRequestDto requestDTO) {
        try {
            UserResponseDto updatedUser = userServiceImpl.forgetPassword(requestDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}




