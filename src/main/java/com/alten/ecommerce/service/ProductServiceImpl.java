package com.alten.ecommerce.service;

import com.alten.ecommerce.mapper.ProductMapper;
import com.alten.ecommerce.model.ProductDto;
import com.alten.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{

    @NonNull private final ProductRepository productRepository;
    @NonNull private final ProductMapper productMapper;


    @Override
    public ProductDto createProduct(ProductDto productDto) {
        requireNonNull(productDto);
        log.info(" create new product ");
        var product = productRepository.save(productMapper.toEntity(productDto));
        return productMapper.toDto(product);
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        log.info(" get all products ");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    public ProductDto getProductById(Long id) {
        log.info(" get product [productId: {} ]: ", id);
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {

        return productRepository.findById(id)
                .map(existingProduct -> {
                    productMapper.updateProductFromDto(productDto, existingProduct);
                    //log.info("Updating product [productId: {}]", existingProduct.getId());
                    return productRepository.save(existingProduct);
                })
                .map(productMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with [productId: {}] " + productDto.getId()));
    }


    @Override
    public void deleteProduct(Long id) {
        log.info("delete product [productId: {} ]: ", id);
        productRepository.deleteById(id);
    }
}
