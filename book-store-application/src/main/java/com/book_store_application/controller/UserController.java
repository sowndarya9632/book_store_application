package com.book_store_application.controller;

import com.book_store_application.model.User;
import com.book_store_application.requestdto.ResetRequestDto;
import com.book_store_application.requestdto.UserRequestDto;
import com.book_store_application.responsedto.TokenResponseDto;
import com.book_store_application.responsedto.UserResponseDto;
import com.book_store_application.serviceImpl.UserServiceImpl;
import com.book_store_application.utility.TokenUtility;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private TokenUtility tokenUtility;
    @Autowired
    private UserServiceImpl userServiceimpl;

    public UserController(UserServiceImpl userServiceimpl) {
        this.userServiceimpl = userServiceimpl;
    }

    @PostMapping("/registeruser")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto user = userServiceimpl.registerUser(userRequestDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserRequestDto userRequestDto) {
        Optional<User> user = userServiceimpl.userLogin(userRequestDto);
        if (user.isPresent()) {
            String token = tokenUtility.createToken(user.get().getId(), user.get().getRole());
            return ResponseEntity.ok(new TokenResponseDto(token));
        } else {
            return new ResponseEntity<>("User login not successfully", HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<UserResponseDto> getUserById(@RequestParam String emailId) {
        try {
            UserResponseDto userResponseDto = userServiceimpl.getUserById(emailId);
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update User
    @PutMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(@RequestParam String email,
                                                      @Valid @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto updatedUser = userServiceimpl.updateUser(email, userRequestDto);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete User
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String emailID) {
        try {
            userServiceimpl.deleteUser(emailID);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    // Reset Password
    @PutMapping("/reset-password")
    public ResponseEntity<UserResponseDto> resetPassword(@RequestParam String emailId,
                                                         @Valid @RequestBody ResetRequestDto resetRequestDto) {
        try {
            UserResponseDto updatedUser = userServiceimpl.resetPassword(emailId, resetRequestDto);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Forget Password
    @PostMapping("/forget-password")
    public ResponseEntity<UserResponseDto> forgetPassword(@Valid @RequestBody UserRequestDto requestDTO) {
        try {
            UserResponseDto updatedUser = userServiceimpl.forgetPassword(requestDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}


