package com.alten.ecommerce.mapper;

import com.alten.ecommerce.entity.Cart;
import com.alten.ecommerce.entity.CartItem;
import com.alten.ecommerce.model.CartDTO;
import com.alten.ecommerce.model.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {


    @Mapping(source = "user.id", target = "userId")
    CartDTO cartToCartDTO(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);

    @Mapping(source = "productId", target = "product.id")
    CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO);
}
