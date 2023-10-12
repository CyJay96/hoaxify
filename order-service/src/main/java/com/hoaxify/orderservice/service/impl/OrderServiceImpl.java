package com.hoaxify.orderservice.service.impl;

import com.hoaxify.orderservice.exception.EntityNotFoundException;
import com.hoaxify.orderservice.mapper.OrderMapper;
import com.hoaxify.orderservice.model.dto.request.OrderRequest;
import com.hoaxify.orderservice.model.dto.response.InventoryResponse;
import com.hoaxify.orderservice.model.dto.response.OrderResponse;
import com.hoaxify.orderservice.model.dto.response.PageResponse;
import com.hoaxify.orderservice.model.entity.Order;
import com.hoaxify.orderservice.model.entity.OrderLineItems;
import com.hoaxify.orderservice.repository.OrderRepository;
import com.hoaxify.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WebClient.Builder webClientBuilder;

    private static final String SKU_CODE_FIELD = "skuCode";
    private static final String INVENTORY_SERVICE_SKU_CODE_URI = "http://inventory-service/api/v0/inventories/bySkuCodes";

    @Override
    public OrderResponse save(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);

        List<String> orderSkuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        List<InventoryResponse> inventoryResponses = webClientBuilder.build().get()
                .uri(INVENTORY_SERVICE_SKU_CODE_URI,
                        uriBuilder -> uriBuilder.queryParam(SKU_CODE_FIELD, orderSkuCodes)
                                .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<InventoryResponse>>() {
                })
                .block();

        boolean isAllProductsInStock = inventoryResponses.stream()
                .allMatch(InventoryResponse::getIsInStock);

        if (Boolean.FALSE.equals(isAllProductsInStock)) {
            throw new EntityNotFoundException("Product is not in stock, please try again later");
        }

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderResponse(savedOrder);
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
