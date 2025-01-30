package com.book_store_application.service;

import com.book_store_application.model.Order;
import com.book_store_application.requestdto.OrderRequestDto;
import com.book_store_application.responsedto.OrderResponseDto;

import java.util.List;

public interface OrderService {
   OrderResponseDto placeOrderForAllItems( String token,OrderRequestDto orderRequestDto);
   OrderResponseDto placeOrderForSpecificItem( Long cartId,OrderRequestDto orderRequestDto);
   boolean cancelOrder(Long orderId);
   List<Order> getAllOrders(boolean cancel);
   List<Order> getAllOrdersForUser(Long userId);
}
