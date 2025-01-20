package com.book_store_application.respository;

import com.book_store_application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCancel(boolean cancel);

    List<Order> findByUserId(Long userId);
}
