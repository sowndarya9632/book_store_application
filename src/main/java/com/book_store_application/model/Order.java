package com.book_store_application.model;


import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;
    private double price;
    private int quantity;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private boolean cancel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public boolean isCancel() {
        return cancel;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}

