package com.book_store_application.model;

import com.book_store_application.responsedto.UserResponseDto;
import jakarta.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Book book;
    private long quantity;
    private double price;

    public Cart(User user, Book book, long quantity, double price) {
        this.user=user;
        this.book=book;
        this.quantity=quantity;
        this.price=price;
    }

    public double getTotalPrice() {
        return price;
    }

    public void setTotalPrice(double totalPrice) {
        this.price = price;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Cart() {
    }
}
