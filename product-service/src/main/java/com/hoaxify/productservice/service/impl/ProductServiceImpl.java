package com.hoaxify.productservice.service.impl;

import com.hoaxify.productservice.exception.EntityNotFoundException;
import com.hoaxify.productservice.mapper.ProductMapper;
import com.hoaxify.productservice.model.dto.request.ProductRequest;
import com.hoaxify.productservice.model.dto.response.PageResponse;
import com.hoaxify.productservice.model.dto.response.ProductResponse;
import com.hoaxify.productservice.model.entity.Product;
import com.hoaxify.productservice.repository.ProductRepository;
import com.hoaxify.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Product product = productRepository.save(productMapper.toProduct(productRequest));
        return productMapper.toProductResponse(product);
    }

    @Override
    public PageResponse<ProductResponse> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> products = productPage.stream()
                .map(productMapper::toProductResponse)
                .toList();

        return PageResponse.<ProductResponse>builder()
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(products.size())
                .content(products)
                .build();
    }

    @Override
    public ProductResponse findById(String id) {
        return productRepository.findById(id)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    @Override
    public ProductResponse update(String id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
        productMapper.updateProduct(productRequest, product);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteById(String id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(Product.class, id);
        }
        productRepository.deleteById(id);
    }
}
