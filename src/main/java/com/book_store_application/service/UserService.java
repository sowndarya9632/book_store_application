package com.book_store_application.service;

import com.book_store_application.model.User;
import com.book_store_application.requestdto.AuthenticationRequest;
import com.book_store_application.requestdto.ResetRequestDto;
import com.book_store_application.requestdto.UserRequestDto;
import com.book_store_application.responsedto.AuthenticationResponse;
import com.book_store_application.responsedto.UserResponseDto;
import jakarta.validation.Valid;

import java.util.Optional;

public interface UserService {
    AuthenticationResponse registerUser(UserRequestDto user);
    Optional<User> userLogin(UserRequestDto requestDTO);
    UserResponseDto getUserById(String emailId);
    UserResponseDto updateUser(String email, UserRequestDto userRequestDto);
    void deleteUser(String emailID);
    UserResponseDto resetPassword(String emailId, ResetRequestDto resetRequestDto);
    UserResponseDto forgetPassword(UserRequestDto requestDTO);
}
