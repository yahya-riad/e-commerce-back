package com.alten.ecommerce.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private Cart cart;

    @ManyToOne
    private Product product;

    private int quantity;

    @Override
    public String toString() {
        return "CartItem{id=" + id + ", product=" + product.getName() + ", quantity=" + quantity + "}";
    }

}
