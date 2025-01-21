package com.book_store_application.controller;

import com.book_store_application.model.Cart;
import com.book_store_application.responsedto.BookResponseDto;
import com.book_store_application.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartServiceimpl;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/{userId}/{bookId}")
    public ResponseEntity<Cart> addToCart(@PathVariable long userId, @PathVariable Integer bookId) {
        Cart cart= cartServiceimpl.addToCart(userId, bookId);
        if(cart!=null) {
            return ResponseEntity.ok(cart);
        }
        else {
            throw new IllegalArgumentException("Users cannot add cart,only admin can add");
        }

    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<Void> removeFromCart(@RequestAttribute("role") String role,@PathVariable Long cartId) {
        if(role==null) {
            throw new IllegalArgumentException("Users and admin not found");
        }
        if(role.equalsIgnoreCase("ADMIN")) {
            cartServiceimpl.removeFromCart(cartId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        else {
            throw new IllegalArgumentException("Users cannot remove cart,only admin can remove");
        }

    }

    @DeleteMapping("/remove-by-user/{userId}")
    public ResponseEntity<Void> removeFromCartByUser(@RequestAttribute("role") String role,@PathVariable long userId) {
        if(role==null) {
            throw new IllegalArgumentException("Users and admin not found");
        }
        if(role.equalsIgnoreCase("USER")) {
            cartServiceimpl.removeAllFromCartByUserId(userId);
            return ResponseEntity.noContent().build();
        }
        else {
            throw new IllegalArgumentException("admin cannot remove cart,only user");
    }

    }

    @PutMapping("/update/{userId}/{cartId}/{quantity}")
    public ResponseEntity<Cart> updateQuantity(@RequestAttribute("role") String role,@PathVariable long userId, @PathVariable long cartId, @RequestParam long quantity) {
        if(role==null) {
            throw new IllegalArgumentException("Users and admin not found");
        }
        if(role.equalsIgnoreCase("ADMIN")) {
            Cart cart = cartServiceimpl.updateQuantity(userId, cartId, quantity);
            return ResponseEntity.ok(cart);
        }
        else {
            throw new IllegalArgumentException("admin can update cart,not by user");
        }

    }

    @GetMapping("/items/{userId}")
    public ResponseEntity<List<Cart>> getAllCartItemsForUser(@RequestAttribute("role") String role,@PathVariable long userId){
        if(role==null) {
            throw new IllegalArgumentException("Users and admin not found");
        }
        if(role.equalsIgnoreCase("USER")) {
            List<Cart> carts = cartServiceimpl.getAllCartItemsForUser(userId);
            return ResponseEntity.ok(carts);
        }
        else {
            throw new IllegalArgumentException("admin can update cart,not by user");
        }
    }

    @GetMapping("/items/all")
    public ResponseEntity<List<Cart>> getAllCartItems() {
        List<Cart> carts = cartServiceimpl.getAllCartItems();
        return ResponseEntity.ok(carts);
    }
}
