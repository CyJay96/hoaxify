package com.hoaxify.orderservice.service;

import com.hoaxify.orderservice.model.dto.request.OrderRequest;
import com.hoaxify.orderservice.model.dto.response.OrderResponse;
import com.hoaxify.orderservice.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse save(OrderRequest orderRequest);

    PageResponse<OrderResponse> findAll(Pageable pageable);

    OrderResponse findById(Long id);

    void deleteById(Long id);
}
