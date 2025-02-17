package com.alten.ecommerce.mapper;

import com.alten.ecommerce.entity.Product;
import com.alten.ecommerce.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity);
}