package com.book_store_application.service;

import com.book_store_application.requestdto.WishlistRequestDto;
import com.book_store_application.responsedto.WishlistResponseDto;

import java.util.List;

public interface WishlistService {
    void addBookToWishlist(WishlistRequestDto requestDto);
    List<WishlistResponseDto> getUserWishlist(Long userId);
    void removeBookFromWishlist(WishlistRequestDto requestDto);
}
