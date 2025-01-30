package com.book_store_application.requestdto;

import com.book_store_application.model.Address;

import java.util.Date;

public class OrderRequestDto {
   private Long userId;
    private Integer bookId;
    private int qty;
    private Address address;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
