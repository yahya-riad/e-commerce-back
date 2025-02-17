package com.alten.ecommerce.service;

import com.alten.ecommerce.entity.Cart;
import com.alten.ecommerce.entity.CartItem;
import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.repository.CartItemRepository;
import com.alten.ecommerce.repository.CartRepository;
import com.alten.ecommerce.repository.ProductRepository;
import com.alten.ecommerce.repository.UserRepository;
import com.alten.ecommerce.mapper.CartMapper;
import com.alten.ecommerce.model.CartDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    private User user;
    private Product product;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Important
        cartService = new CartServiceImpl(cartRepository, cartItemRepository, productRepository, userRepository, cartMapper);

        product = Product.builder()
                .id(1L)
                .name("Test")
                .description("Description")
                .price(100.0)
                .build();

        user = User.builder()
                .id(1L)
                .email("admin@admin.com")
                .password("password")
                .build();


        cart = Cart.builder()
                .user(user)
                .items(null)
                .build();
    }

    @Test
    public void testAddItemToCart_CartExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        var cartItem = CartItem.builder()
                .product(product)
                .quantity(1)
                .build();

        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        cartService.addItemToCart(user.getEmail(), product.getId(), 1);

        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void testAddItemToCart_CartDoesNotExist() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.empty());
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        var newCart = Cart.builder()
                .user(user)
                .items(null)
                .build();
        when(cartRepository.save(any(Cart.class))).thenReturn(newCart);

        cartService.addItemToCart(user.getEmail(), product.getId(), 1);

        verify(cartRepository, times(1)).save(any(Cart.class));
    }

    @Test
    public void testGetCartForUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findByUserId(user.getId())).thenReturn(Optional.of(cart));
        when(cartMapper.cartToCartDTO(any(Cart.class))).thenReturn(new CartDTO());

        var cartDTO = cartService.getCartForUser(user.getEmail());

        assertNotNull(cartDTO);
    }

    @Test
    public void testRemoveItemFromCart() {
        var cartItem = CartItem.builder()
                        .id(1L)
                        .build();

        when(cartItemRepository.findById(cartItem.getId())).thenReturn(Optional.of(cartItem));

        cartService.removeItemFromCart(user.getEmail(), cartItem.getId());

        verify(cartItemRepository, times(1)).delete(cartItem);
    }
}

