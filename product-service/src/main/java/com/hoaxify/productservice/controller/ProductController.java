package com.hoaxify.productservice.controller;

import com.hoaxify.productservice.model.dto.request.ProductRequest;
import com.hoaxify.productservice.model.dto.response.APIResponse;
import com.hoaxify.productservice.model.dto.response.PageResponse;
import com.hoaxify.productservice.model.dto.response.ProductResponse;
import com.hoaxify.productservice.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.hoaxify.productservice.controller.ProductController.PRODUCT_API_PATH;

@RestController
@RequestMapping(PRODUCT_API_PATH)
@RequiredArgsConstructor
public class ProductController {

    public static final String PRODUCT_API_PATH = "/api/v0/products";

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse<ProductResponse>> save(
            @RequestBody @Valid ProductRequest productRequest
    ) {
        ProductResponse product = productService.save(productRequest);

        return APIResponse.of(
                "Product with ID " + product.getId() + " was created",
                PRODUCT_API_PATH,
                HttpStatus.CREATED,
                product
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<ProductResponse>>> findAll(Pageable pageable) {
        PageResponse<ProductResponse> products = productService.findAll(pageable);

        return APIResponse.of(
                "All Products: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize(),
                PRODUCT_API_PATH,
                HttpStatus.OK,
                products
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> findById(@PathVariable @NotBlank String id) {
        ProductResponse product = productService.findById(id);

        return APIResponse.of(
                "Product with ID " + product.getId() + " was found",
                PRODUCT_API_PATH + "/" + id,
                HttpStatus.OK,
                product
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> update(
            @PathVariable @NotBlank String id,
            @RequestBody @Valid ProductRequest productRequest
    ) {
        ProductResponse product = productService.update(id, productRequest);

        return APIResponse.of(
                "Changes were applied to the Product with ID " + id,
                PRODUCT_API_PATH + "/" + id,
                HttpStatus.OK,
                product
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> updatePartially(
            @PathVariable @NotBlank String id,
            @RequestBody ProductRequest productRequest
    ) {
        ProductResponse product = productService.update(id, productRequest);

        return APIResponse.of(
                "Partial changes were applied to the Product with ID " + id,
                PRODUCT_API_PATH + "/" + id,
                HttpStatus.OK,
                product
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteById(@PathVariable @NotBlank String id) {
        productService.deleteById(id);

        return APIResponse.of(
                "Product with ID " + id + " was deleted",
                PRODUCT_API_PATH + "/" + id,
                HttpStatus.OK,
                null
        );
    }
}
