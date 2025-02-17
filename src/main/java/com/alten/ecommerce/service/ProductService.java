package com.alten.ecommerce.service;

import com.alten.ecommerce.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    Page<ProductDto> getAllProducts(Pageable pageable);
    ProductDto getProductById(Long id);
    ProductDto updateProduct(Long id, ProductDto productDto);
    void deleteProduct(Long id);
}
