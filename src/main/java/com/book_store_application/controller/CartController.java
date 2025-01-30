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

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartServiceimpl;

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add/{userId}/{bookId}/{requestQuantity}")
    public ResponseEntity<Cart> addToCart(@PathVariable long userId, @PathVariable Integer bookId,@PathVariable int requestQuantity) {
        Cart cart = cartServiceimpl.addToCart(userId, bookId,requestQuantity);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            throw new IllegalArgumentException("Users cannot add cart,only admin can add");
        }

    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartId) {
            cartServiceimpl.removeFromCart(cartId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{userId}/{cartId}/{quantity}")
    public ResponseEntity<Cart> updateQuantity(@PathVariable long userId, @PathVariable long cartId, @PathVariable long quantity) {
            Cart cart = cartServiceimpl.updateQuantity(userId, cartId, quantity);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            throw new IllegalArgumentException("Users cannot add cart,only admin can add");
        }

    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/items/{userId}")
    public ResponseEntity<List<Cart>> getAllCartItemsForUser(@PathVariable long userId){
            List<Cart> carts = cartServiceimpl.getAllCartItemsForUser(userId);
        if (carts != null) {
            return ResponseEntity.ok(carts);
        } else {
            throw new IllegalArgumentException("Users cannot add cart,only admin can add");
        }
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/items/all")
    public ResponseEntity<List<Cart>> getAllCartItems() {
        List<Cart> carts = cartServiceimpl.getAllCartItems();
        return ResponseEntity.ok(carts);
    }
}
