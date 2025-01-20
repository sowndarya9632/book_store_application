package com.book_store_application.responsedto;

import com.book_store_application.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;  // Add token field
    private String message;
    private User user;

    public AuthenticationResponse(String token, String message, User user) {
        this.token = token;
        this.message = message;
        this.user = user;
    }
}
