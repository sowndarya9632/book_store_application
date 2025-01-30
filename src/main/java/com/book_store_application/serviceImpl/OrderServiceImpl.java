package com.book_store_application.serviceImpl;

import com.book_store_application.filter.JwtService;
import com.book_store_application.model.*;
import com.book_store_application.requestdto.OrderRequestDto;
import com.book_store_application.responsedto.OrderResponseDto;
import com.book_store_application.respository.*;
import com.book_store_application.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    private UserRepository userRepository;

    private BookRepository bookRepository;

    private CartRepository cartRepository;

    private AddressRepository addressRepository;

    private final JwtService jwtService;


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, BookRepository bookRepository, CartRepository cartRepository, AddressRepository addressRepository, JwtService jwtService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.cartRepository = cartRepository;
        this.addressRepository=addressRepository;
        this.jwtService = jwtService;
    }
    @Transactional
    public OrderResponseDto placeOrderForAllItems(String token,OrderRequestDto orderRequestDto) {
        Long userId = getUserIdFromToken(token); // Extract user ID from token
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

       // User user = userRepository.findById(orderRequestDto.getUserId())
              //  .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cart = cartRepository.findByUser(user);
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Address address = orderRequestDto.getAddress();
           System.out.println(address);
        if (address != null) {
            address = addressRepository.save(address);
            System.out.println(address);
        } else {
            throw new RuntimeException("Address cannot be null");
        }

        double totalPrice = 0.0;
        Order order = null;

        for (Cart cartItem : cart) {
            Book book = cartItem.getBook();
            long quantity = cartItem.getQuantity();
            System.out.println(quantity);
            if (quantity > book.getQuantity()) {
                throw new RuntimeException("Insufficient stock for book: " + book.getBookName());
            }

            double itemPrice = book.getPrice() * quantity;
            totalPrice += itemPrice;

            order = createOrder(user, book, quantity, itemPrice, address);

            orderRepository.save(order);

            book.setQuantity(book.getQuantity() - quantity);
            bookRepository.save(book);

            cartRepository.delete(cartItem);
        }

        return mapToDto(order);
    }

    @Override
    public OrderResponseDto placeOrderForSpecificItem(Long cartId,OrderRequestDto orderRequestDto) {
       /* User user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!Long.valueOf(cart.getUser().getId()).equals(orderRequestDto.getUserId())) {
            throw new RuntimeException("This cart does not belong to the user");
        }

        Book book = cart.getBook();
        long quantityInCart = cart.getQuantity();

        if (book.getQuantity() < quantityInCart) {
            throw new RuntimeException("Insufficient stock for book: ");
        }

        Order order = createOrder(user, book, cart.getQuantity(), cart.getQuantity() * book.getPrice(), orderRequestDto.getAddress());
        orderRepository.save(order);

        book.setQuantity(book.getQuantity() - cart.getQuantity());
        bookRepository.save(book);
        cartRepository.delete(cart);
        return mapToDto(order);*/
     return null;
    }
@Override
    public boolean cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setCancel(true);
        orderRepository.save(order);
        return true;
    }
@Override
    public List<Order> getAllOrders(boolean cancel) {
        return orderRepository.findByCancel(cancel);
    }
@Override
    public List<Order> getAllOrdersForUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }


    private OrderResponseDto mapToDto(Order order) {
        OrderResponseDto orderResponseDto=new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setAddress(order.getAddress());
        orderResponseDto.setOrderDate(LocalDate.now());
        orderResponseDto.setPrice(order.getPrice());
        orderResponseDto.setQuantity(order.getQuantity());
        orderResponseDto.setBook(order.getBook());
        orderResponseDto.setUser(order.getUser());
        return orderResponseDto;
    }

    private Order createOrder(User user, Book book, long quantity, double price, Address address){
        Order order = new Order();
        order.setUser(user);
        order.setBook(book);
        order.setQuantity((int) quantity);
        order.setPrice(price);
        order.setOrderDate(LocalDate.now());
        order.setCancel(false);
        order.setAddress(address);
        return order;
    }
    public Long getUserIdFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        return jwtService.extractUserId(token);
    }
}





    