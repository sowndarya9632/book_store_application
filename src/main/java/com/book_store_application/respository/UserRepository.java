package com.book_store_application.respository;

import com.book_store_application.model.User;
import com.book_store_application.responsedto.TokenResponseDto;
import com.book_store_application.responsedto.UserResponseDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByfirstNameAndPassword( String firstName,String password);
    Optional<User> findByEmailId(String emailId);
    boolean existsByFirstName(String firstName);  // Correct method name
}
