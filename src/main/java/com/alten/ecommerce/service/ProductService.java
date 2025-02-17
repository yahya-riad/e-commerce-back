package com.alten.ecommerce.service;

import com.alten.ecommerce.model.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto updateProduct(ProductDto productDto);
    void deleteProduct(Long id);
}
