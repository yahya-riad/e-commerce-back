package com.alten.ecommerce.entity;

import com.alten.ecommerce.model.InventoryStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private int shellId;
    @Enumerated(EnumType.STRING)
    private InventoryStatusEnum inventoryStatus;
    private int rating;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = Instant.now();
    }
}
