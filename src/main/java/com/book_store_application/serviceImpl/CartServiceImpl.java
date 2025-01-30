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

    private CartRepository cartRepository;
    private BookRepository bookRepository;
    private JwtService jwtService;
    private UserRepository userRepository;
    @Autowired
    public CartServiceImpl(CartRepository cartRepository, BookRepository bookRepository,
                           JwtService jwtService, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public Cart addToCart(long userId, Integer bookId, int requestQuantity) {
        // Retrieve the user from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Cart existingCart = cartRepository.findByUserAndBook(user, book);
        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + requestQuantity);
            return cartRepository.save(existingCart);
        }
        Cart newCart = new Cart(user, book, requestQuantity, book.getPrice());
        return cartRepository.save(newCart);
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
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (!cart.getUser().equals(user)) {
            throw new RuntimeException("Cart does not belong to this user");
        }

        Book book = cart.getBook();
        if (quantity > book.getQuantity()) {
            throw new RuntimeException("Insufficient stock for book: " + book.getBookName());
        }

        cart.setQuantity(quantity);
        return cartRepository.save(cart);
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
