package com.book_store_application.requestdto;

import com.book_store_application.model.Book;
import com.book_store_application.model.User;
import jakarta.persistence.ManyToOne;

public class CartRequestDto {
    private User user;
    private Book book;
    private long quantity;
    private long totalPrice;

}
