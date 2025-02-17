package com.alten.ecommerce.controller;

import com.alten.ecommerce.model.CartDTO;
import com.alten.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public CartDTO getCart() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return cartService.getCartForUser(email);
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam Long productId, @RequestParam int quantity) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.addItemToCart(email, productId, quantity);
    }

    @DeleteMapping("/remove")
    public void removeProductFromCart(@RequestParam Long cartItemId) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        cartService.removeItemFromCart(email, cartItemId);
    }
}

