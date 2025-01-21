package com.book_store_application.serviceImpl;

import com.book_store_application.model.Book;
import com.book_store_application.model.User;
import com.book_store_application.model.Wishlist;
import com.book_store_application.requestdto.WishlistRequestDto;
import com.book_store_application.responsedto.WishlistResponseDto;
import com.book_store_application.respository.BookRepository;
import com.book_store_application.respository.UserRepository;
import com.book_store_application.respository.WishlistRepository;
import com.book_store_application.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private WishlistRepository wishlistRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    private UserRepository userRepository;

    public WishlistServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private BookRepository bookRepository;

    public WishlistServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBookToWishlist(WishlistRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (wishlistRepository.existsByUserAndBook(user, book)) {
            throw new RuntimeException("Book already in wishlist");
        }
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setBook(book);

        wishlistRepository.save(wishlist);
    }

    @Override
    public List<WishlistResponseDto> getUserWishlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Wishlist> wishlistEntries = wishlistRepository.findByUser(user);

        return wishlistEntries.stream().map(wishlist -> {
            WishlistResponseDto responseDto = new WishlistResponseDto();
            responseDto.setId(wishlist.getId());
            responseDto.setUserId(wishlist.getUser().getId());
            responseDto.setBookId(wishlist.getBook().getId());
            responseDto.setAuthorName(wishlist.getBook().getAuthorName());
            return responseDto;
        }).collect(Collectors.toList());
    }



    @Override
    public void removeBookFromWishlist(WishlistRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Wishlist wishlist = wishlistRepository.findByUserAndBook(user, book);

        wishlistRepository.delete(wishlist);
    }
}

