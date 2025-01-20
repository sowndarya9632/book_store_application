package com.book_store_application.responsedto;

public class TokenResponseDto {

    private String token;

    // Constructor
    public TokenResponseDto(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }
}
