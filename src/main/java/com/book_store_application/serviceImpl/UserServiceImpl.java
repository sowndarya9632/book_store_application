package com.book_store_application.serviceImpl;

import com.book_store_application.filter.JwtService;
import com.book_store_application.model.User;
import com.book_store_application.requestdto.AuthenticationRequest;
import com.book_store_application.requestdto.ResetRequestDto;
import com.book_store_application.requestdto.UserRequestDto;
import com.book_store_application.responsedto.AuthenticationResponse;
import com.book_store_application.responsedto.UserResponseDto;
import com.book_store_application.respository.UserRepository;
import com.book_store_application.service.EmailService;
import com.book_store_application.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse registerUser(UserRequestDto userRequestDto) {
            var user = User.builder()
                    .firstName(userRequestDto.getFirstName())
                    .lastName(userRequestDto.getLastName())
                    .emailId(userRequestDto.getEmailId())
                    .dob(userRequestDto.getDob())
                    .registeredDate(userRequestDto.getRegisteredDate())
                    .updatedDate(LocalDate.now())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .role(userRequestDto.getRole())
                    .build();
            var savedUser = userRepository.save(user);
            return AuthenticationResponse.builder()
                    .message("User registered successfully")
                    .user(savedUser)
                    .build();
        }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailId(),
                        request.getPassword()
                )
        );

        // Retrieve the user from the database
        var user = userRepository.findByEmailId(request.getEmailId())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

        // Generate the JWT token
        var jwtToken = jwtService.generateToken(user);

        // Return the response with the token, message, and user
        return AuthenticationResponse.builder()
                .token(jwtToken) // Set the token here
                .message("Login successful") // Keep the message concise
                .user(user) // Include the user in the response
                .build();
    }



    public Optional<User> userLogin(UserRequestDto userRequestDto) {
        Optional<User> userLogin = userRepository.findByfirstNameAndPassword(userRequestDto.getFirstName(), userRequestDto.getPassword());

        if (userLogin.isPresent()) {

            return userLogin;
        } else {
            return null;
        }
    }

    private UserResponseDto mapTODto(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setFirstName(user.getFirstName());
        responseDto.setLastName(user.getLastName());
        responseDto.setEmailId(user.getEmailId());
        responseDto.setRole(user.getRole());
        responseDto.setDob(user.getDob());
        responseDto.setPassword(user.getPassword());
        responseDto.setRegisteredDate(user.getRegisteredDate());
        responseDto.setUpdatedDate(user.getUpdatedDate());
        //responseDto.setToken(token);
        return responseDto;

    }


    @Override
    public UserResponseDto getUserById(String emailId) {
        User user = userRepository.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("emailId not found"));
        return mapTODto(user);

    }

    @Override
    public UserResponseDto updateUser(String emailId, UserRequestDto userRequestDto) {
        Optional<User> userOpt = userRepository.findByEmailId(emailId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFirstName(userRequestDto.getFirstName());
            user.setLastName(userRequestDto.getLastName());
            user.setRole(userRequestDto.getRole());
            userRepository.save(user);
            return mapTODto(user);
        }
        return null;
    }


    @Override
    public void deleteUser(String emailId) {
        userRepository.findByEmailId(emailId).orElseThrow(() -> new RuntimeException("emailId not found"));

    }

    @Override
    public UserResponseDto resetPassword(String emailId, ResetRequestDto resetRequestDto) {
        Optional<User> userOpt = userRepository.findByEmailId(emailId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(resetRequestDto.getOldPassword())) {
                user.setPassword(resetRequestDto.getNewPassword());
                user.setUpdatedDate(LocalDate.now());
                userRepository.save(user);
                return mapTODto(user);
            } else {
                throw new IllegalArgumentException("Current password is incorrect");
            }
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public UserResponseDto forgetPassword(UserRequestDto requestDTO) {
        Optional<User> userOpt = userRepository.findByEmailId(requestDTO.getEmailId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String tempPassword = generateTempPassword();
            user.setPassword(tempPassword);
            user.setUpdatedDate(LocalDate.now());
            userRepository.save(user);
            emailService.sendPasswordResetEmail(user.getEmailId(), tempPassword);
            return mapTODto(user);
        } else {
            throw new IllegalArgumentException("User with this email doesn't exist");
        }
    }

    private String generateTempPassword() {
        return "Temp1234!";
    }


}