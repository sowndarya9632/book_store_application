package com.book_store_application.responsedto;

import com.book_store_application.model.Image;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private Integer id;
    private String bookName;
    private String authorName;
    private String description;
    private Image image;
    private double price;
    private Long quantity;


    public BookResponseDto(Integer id, String bookName, String authorName, double price, String description, Long quantity, Image image) {
        this.id = id;
        this.bookName = bookName;
        this.authorName = authorName;
        this.description = description;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
