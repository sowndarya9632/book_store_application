package com.book_store_application.respository;

import com.book_store_application.model.Book;
import com.book_store_application.model.User;
import com.book_store_application.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    boolean existsByUserAndBook(User user, Book book);
    List<Wishlist> findByUser(User user);
    Wishlist findByUserAndBook(User user, Book book);
}



