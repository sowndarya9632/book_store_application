package com.book_store_application.serviceImpl;

import com.book_store_application.exception.ResourceNotFoundException;
import com.book_store_application.model.Book;
import com.book_store_application.model.Cart;
import com.book_store_application.model.User;
import com.book_store_application.responsedto.UserResponseDto;
import com.book_store_application.respository.BookRepository;
import com.book_store_application.respository.CartRepository;
import com.book_store_application.respository.UserRepository;
import com.book_store_application.service.CartService;
import com.book_store_application.filter.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    // Add to Cart
    public Cart addToCart(long userId , Integer id) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Cart existingCart = cartRepository.findByUserAndBook(user, book);
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + 1);
            cartRepository.save(existingCart);
            return existingCart;
        }

        Cart cart = new Cart(user, book,book.getQuantity(), book.getPrice());
        return cartRepository.save(cart);
    }

    public void removeFromCart(long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            cartRepository.delete(cart.get());
        } else {
            throw new ResourceNotFoundException("Cart not found with id: " + cartId);
        }
    }

    public void removeAllFromCartByUserId(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Cart> carts = cartRepository.findByUser(user);
        cartRepository.deleteAll(carts);
    }

    public Cart updateQuantity(long userId, long cartId, long quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Optional<Cart> cart = cartRepository.findById(cartId);

        if (cart.isPresent() && cart.get().getUser().equals(user)) {
            Cart cartItem = cart.get();
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem);
            return cartItem;
        } else {
            throw new ResourceNotFoundException("Cart not found for this user with id: " + cartId);
        }
    }
    public List<Cart> getAllCartItemsForUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return cartRepository.findByUser(user);
    }
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

}
