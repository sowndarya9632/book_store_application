package com.book_store_application.controller;

import com.book_store_application.model.Order;
import com.book_store_application.requestdto.OrderRequestDto;
import com.book_store_application.responsedto.OrderResponseDto;
import com.book_store_application.serviceImpl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceimpl;

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDto> placeOrderForAllItems(@RequestBody OrderRequestDto orderRequestDto) {
      OrderResponseDto  orderResponseDto  = orderServiceimpl.placeOrderForAllItems(orderRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDto);
    }

    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<Boolean> cancelOrder(@PathVariable Long orderId) {
        boolean canceled = orderServiceimpl.cancelOrder(orderId);
        return ResponseEntity.ok(canceled);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(defaultValue = "false") boolean cancel) {
        List<Order> orders = orderServiceimpl.getAllOrders(cancel);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersForUser(@PathVariable Long userId) {
        List<Order> orders = orderServiceimpl.getAllOrdersForUser(userId);
        return ResponseEntity.ok(orders);
    }
}