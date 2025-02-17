package com.alten.ecommerce.controller;

import com.alten.ecommerce.model.ProductDto;
import com.alten.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    public static final String ADMIN_MAIL = "admin@admin.com";
    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        if (isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @GetMapping
    public Page<ProductDto> getProducts(
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        if (isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isAdmin() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return !ADMIN_MAIL.equals(email);
    }
}
