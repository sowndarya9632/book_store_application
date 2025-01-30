package com.book_store_application.service;

import com.book_store_application.model.Book;
import com.book_store_application.model.Cart;
import com.book_store_application.model.User;

import java.util.List;

public interface CartService {
    Cart addToCart(long userId, Integer bookId,int requestQuantity);
    void removeFromCart(long cartId);
    void removeAllFromCartByUserId(long userId );
    Cart updateQuantity( long userId, long cartId, long quantity);
    List<Cart> getAllCartItemsForUser(long userId );
    List<Cart> getAllCartItems();
}
