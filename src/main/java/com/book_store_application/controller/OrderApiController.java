package com.book_store_application.controller;

import com.book_store_application.model.Cart;
import com.book_store_application.model.Order;
import com.book_store_application.model.User;
import com.book_store_application.requestdto.OrderRequestDto;
import com.book_store_application.responsedto.OrderResponseDto;
import com.book_store_application.serviceImpl.AddressService;
import com.book_store_application.serviceImpl.OrderServiceImpl;
import com.book_store_application.filter.JwtService;
import com.book_store_application.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH,RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/v1/order")
public class OrderApiController {
    @Autowired
    JwtService jwtService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderServiceImpl orderServiceimpl;
    @Autowired
   private UserServiceImpl userService;
    @GetMapping
    public String message() {
        return "Hello";
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/place/allitems")
    public ResponseEntity<OrderResponseDto> placeOrderForAllItems(@RequestHeader("Authorization") String authorizationHeader
            ,@RequestBody OrderRequestDto orderRequestDto) {
        String token = authorizationHeader.trim();
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove the "Bearer " part
        }
        OrderResponseDto order= orderServiceimpl.placeOrderForAllItems(token,orderRequestDto);
        if(order!=null) {
            return ResponseEntity.ok(order);
        }
        else {
            throw new IllegalArgumentException("Users cannot add order,only admin can place");
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long orderId) {
        try {
            // Cancel the order
            orderServiceimpl.cancelOrder(orderId);

            Map<String, String> response = new HashMap<>();
            response.put("success", "true");
            response.put("message", "Order has been canceled successfully.");

            // Return 200 OK with success message
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // If there's an error, return a bad request with the error message
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("success", "false");
            errorResponse.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getAllForUser/{userId}")
    public ResponseEntity<List<Order>> getAllOrdersForUser(@PathVariable Long userId) {
        try {
            List<Order> orders = orderServiceimpl.getAllOrdersForUser(userId);
            if (orders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
