package com.hoaxify.orderservice.service.impl;

import com.hoaxify.orderservice.exception.EntityNotFoundException;
import com.hoaxify.orderservice.mapper.OrderMapper;
import com.hoaxify.orderservice.model.dto.request.OrderRequest;
import com.hoaxify.orderservice.model.dto.response.OrderResponse;
import com.hoaxify.orderservice.model.dto.response.PageResponse;
import com.hoaxify.orderservice.model.entity.Order;
import com.hoaxify.orderservice.repository.OrderRepository;
import com.hoaxify.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        Order order = orderRepository.save(orderMapper.toOrder(orderRequest));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public PageResponse<OrderResponse> findAll(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);

        List<OrderResponse> orders = orderPage.stream()
                .map(orderMapper::toOrderResponse)
                .toList();

        return PageResponse.<OrderResponse>builder()
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(orders.size())
                .content(orders)
                .build();
    }

    @Override
    public OrderResponse findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new EntityNotFoundException(Order.class, id));
    }

    @Override
    public void deleteById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException(Order.class, id);
        }
        orderRepository.deleteById(id);
    }
}
