package com.book_store_application.respository;

import com.book_store_application.model.Book;
import com.book_store_application.model.Cart;
import com.book_store_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserAndBook(User user, Book book);

    List<Cart> findByUser(User user);

    void deleteByUserIdAndBookId(Long userId, Integer bookId);

}
