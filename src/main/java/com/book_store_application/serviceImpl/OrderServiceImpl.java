package com.book_store_application.serviceImpl;

import com.book_store_application.model.Book;
import com.book_store_application.model.Cart;
import com.book_store_application.model.Order;
import com.book_store_application.model.User;
import com.book_store_application.requestdto.OrderRequestDto;
import com.book_store_application.responsedto.OrderResponseDto;
import com.book_store_application.respository.BookRepository;
import com.book_store_application.respository.CartRepository;
import com.book_store_application.respository.OrderRepository;
import com.book_store_application.respository.UserRepository;
import com.book_store_application.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private UserRepository userRepository;

    public OrderServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private BookRepository bookRepository;

    public OrderServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private CartRepository cartRepository;

    public OrderServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public OrderResponseDto placeOrderForAllItems(OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));
        List<Cart> cart = cartRepository.findByUser(user);
        if (cart.isEmpty()) {
            throw new RuntimeException("cart is empty");
        }
        Order order = null;
        double totalPrice = 0.0;
        for (Cart cartitem : cart) {
            Book book = cartitem.getBook();
            long Quantity = cartitem.getQuantity();
            if (Quantity < book.getQuantity()) {
                throw new RuntimeException("Insufficient quantity in cart");
            }
            double itemPrice = book.getPrice() * cartitem.getQuantity();
            totalPrice += itemPrice;
            order = createOrder(user, book, cartitem.getQuantity(), book.getPrice() * cartitem.getQuantity(), orderRequestDto.getAddress());

            orderRepository.save(order);
            book.setQuantity(book.getQuantity() - cartitem.getQuantity());
            bookRepository.save(book);
            cartRepository.delete(cartitem);
        }
        return mapToDto(order);
    }

    @Override
    public OrderResponseDto placeOrderForSpecificItem(Long cartId,OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId())
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
        return mapToDto(order);

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

    private Order createOrder(User user, Book book, long quantity, double price, String address){
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

}





    