package com.book_store_application.controller;

import com.book_store_application.requestdto.OrderRequestDto;
import com.book_store_application.responsedto.OrderResponseDto;
import com.book_store_application.serviceImpl.OrderServiceImpl;
import com.book_store_application.filter.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {
    @Autowired
    JwtService jwtService;

    @Autowired
    private OrderServiceImpl orderServiceimpl;


    @GetMapping
    public String message() {
        return "Hello";
    }

    @PostMapping("/place/allitems")
    public ResponseEntity<OrderResponseDto> placeOrderForAllItems(@RequestAttribute("role") String role, @RequestBody OrderRequestDto orderRequestDto) {

        if ("ADMIN".equals(role)) {
       OrderResponseDto order   =orderServiceimpl.placeOrderForAllItems(orderRequestDto);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("admin can place order,not by user");
        }
    }
    @PostMapping("/place/specificitem/{cartId}")
    public ResponseEntity<OrderResponseDto> placeOrderForSpecificItem(@RequestAttribute("role") String role,@PathVariable Long cartId, @RequestBody OrderRequestDto orderRequestDto) {

        if ("ADMIN".equals(role)) {
            OrderResponseDto order   =orderServiceimpl.placeOrderForSpecificItem(cartId,orderRequestDto);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("admin can place order,not by user");
        }
    }
    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<OrderResponseDto> cancelOrder(@RequestAttribute("role") String role, @PathVariable  Long orderId ) {

        if ("ADMIN".equals(role)) {
            orderServiceimpl.cancelOrder(orderId);
            return ResponseEntity.noContent().build();
        } else {
            throw new IllegalArgumentException("admin can place order,not by user");
        }
    }
}
