package com.hoaxify.productservice.mapper;

import com.hoaxify.productservice.model.dto.request.ProductRequest;
import com.hoaxify.productservice.model.dto.response.ProductResponse;
import com.hoaxify.productservice.model.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequest productRequest);

    ProductResponse toProductResponse(Product product);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    void updateProduct(ProductRequest productRequest, @MappingTarget Product product);
}
