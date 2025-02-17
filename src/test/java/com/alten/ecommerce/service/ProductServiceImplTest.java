package com.alten.ecommerce.service;



import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.mapper.ProductMapper;
import com.alten.ecommerce.model.ProductDto;
import com.alten.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;
    private PageRequest pageable;
    private Page<Product> pageOfProducts;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Important
        productService = new ProductServiceImpl(productRepository, productMapper);
        product = Product.builder()
                .id(1L)
                .name("Test")
                .description("Description")
                .price(100.0)
                .quantity(10)
                .build();

        productDto = ProductDto.builder()
                .id(1L)
                .name("Test")
                .description("Description")
                .price(100.0)
                .quantity(10)
                .build();

        pageable = PageRequest.of(0, 1, Sort.by("name").ascending());
        pageOfProducts = new PageImpl<>(Arrays.asList(product), pageable, 1);
    }

    @Test
    void createProduct_ShouldReturnProductDto() {
        when(productMapper.toEntity(any())).thenReturn(product);
        when(productRepository.save(any())).thenReturn(product);
        when(productMapper.toDto(any())).thenReturn(productDto);
        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals(productDto.getName(), result.getName());
        verify(productRepository).save(any());
    }

    @Test
    void getAllProducts_ShouldReturnListOfProductDtos_WithPaginationAndSorting() {

        when(productRepository.findAll(pageable)).thenReturn(pageOfProducts);
        when(productMapper.toDto(any())).thenReturn(productDto);

        // When
        Page<ProductDto> result = productService.getAllProducts(pageable);

        // Then
        assertFalse(result.isEmpty());
        assertEquals(1, result.getSize());
        assertEquals("Test", result.getContent().getFirst().getName());
    }

    @Test
    void getProductById_ShouldReturnProductDto() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productMapper.toDto(any())).thenReturn(productDto);

        ProductDto result = productService.getProductById(1L);

        assertNotNull(result);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProductDto() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productMapper.toDto(any())).thenReturn(productDto);
        when(productRepository.save(any())).thenReturn(product);

        ProductDto result = productService.updateProduct(1L, productDto);

        assertNotNull(result);
        verify(productRepository).save(any());
    }

    @Test
    void deleteProduct_ShouldCallRepositoryDelete() {
        doNothing().when(productRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> productService.deleteProduct(1L));
        verify(productRepository).deleteById(1L);
    }
}
