package com.alten.ecommerce.service;

import com.alten.ecommerce.entity.Cart;
import com.alten.ecommerce.entity.CartItem;
import com.alten.ecommerce.entity.User;
import com.alten.ecommerce.mapper.CartMapper;
import com.alten.ecommerce.model.CartDTO;
import com.alten.ecommerce.repository.CartItemRepository;
import com.alten.ecommerce.repository.CartRepository;
import com.alten.ecommerce.repository.ProductRepository;
import com.alten.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CartMapper cartMapper;

    @Override
    public CartDTO getCartForUser(String email) {
        log.info("Get cart for user email: " + email);

        var user = findUser(email);

        var cart = findCart(user.getId());

        return cartMapper.cartToCartDTO(cart);
    }

    @Override
    public void addItemToCart(String email, Long productId, int quantity) {
        var user = findUser(email);

        var cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .items(new ArrayList<>())
                            .build();
                    cartRepository.save(newCart);
                    return newCart;
                });

        var product = productRepository.findById(productId).orElseThrow(() ->
                new RuntimeException("Product not found: productId: " + productId));

        var item = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();
        cartItemRepository.save(item);
    }

    @Override
    public void removeItemFromCart(String email, Long cartItemId) {
        var item = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new RuntimeException("Item not found : cartItemId: " + cartItemId));
        cartItemRepository.delete(item);
    }

    public User findUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));
    }

    public Cart findCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No cart found for user id : " + userId));
    }
}
