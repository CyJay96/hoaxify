package com.hoaxify.productservice.service;

import com.hoaxify.productservice.model.dto.request.ProductRequest;
import com.hoaxify.productservice.model.dto.response.PageResponse;
import com.hoaxify.productservice.model.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse save(ProductRequest productRequest);

    PageResponse<ProductResponse> findAll(Pageable pageable);

    ProductResponse findById(String id);

    ProductResponse update(String id, ProductRequest productRequest);

    void deleteById(String id);
}
