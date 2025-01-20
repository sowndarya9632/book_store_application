package com.book_store_application.responsedto;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Integer id;
    private String bookName;
    private String authorName;
    private String description;
    @Lob
    private byte[] logo;
    private double price;
    private Long quantity;


    public BookResponseDto(Integer id, String bookName, String authorName, double price, String description, Long quantity, byte[] logo) {
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.description = description;
        this.logo = logo;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
