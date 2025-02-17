package com.alten.ecommerce.service;

import com.alten.ecommerce.model.CartDTO;


public interface CartService {
    CartDTO getCartForUser(String email);
    void addItemToCart(String email, Long productId, int quantity);
    void removeItemFromCart(String email, Long cartItemId);
}
