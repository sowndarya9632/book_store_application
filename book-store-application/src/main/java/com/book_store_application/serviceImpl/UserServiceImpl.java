package com.book_store_application.serviceImpl;

import com.book_store_application.model.User;
import com.book_store_application.requestdto.ResetRequestDto;
import com.book_store_application.requestdto.UserRequestDto;
import com.book_store_application.responsedto.UserResponseDto;
import com.book_store_application.respository.UserRepository;
import com.book_store_application.service.EmailService;
import com.book_store_application.service.UserService;
import com.book_store_application.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenUtility tokenutility;
    @Autowired
    private EmailService emailService;

    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if (userRequestDto.getFirstName() == null || userRequestDto.getPassword() == null) {
            throw new IllegalArgumentException("Data required");
        }
        boolean isFirstname = userRepository.existsByFirstName(userRequestDto.getFirstName());
        if (isFirstname) {
            throw new RuntimeException("User already exists");
        }
        User user=new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setPassword(userRequestDto.getPassword());
        user.setLastName(user.getLastName());
        user.setEmailId(userRequestDto.getEmailId());
        user.setRole(userRequestDto.getRole());
        user.setUpdatedDate(userRequestDto.getUpdatedDate());
        user.setRegisteredDate(userRequestDto.getRegisteredDate());
        user.setLastName(userRequestDto.getLastName());
        user.setDob(userRequestDto.getDob());
        userRepository.save(user);
        return mapTODto(user);
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