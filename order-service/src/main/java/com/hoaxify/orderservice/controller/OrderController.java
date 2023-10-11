package com.hoaxify.orderservice.controller;

import com.hoaxify.orderservice.model.dto.request.OrderRequest;
import com.hoaxify.orderservice.model.dto.response.APIResponse;
import com.hoaxify.orderservice.model.dto.response.OrderResponse;
import com.hoaxify.orderservice.model.dto.response.PageResponse;
import com.hoaxify.orderservice.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OrderController.ORDER_API_PATH)
@RequiredArgsConstructor
public class OrderController {

    public static final String ORDER_API_PATH = "/api/v0/orders";

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<APIResponse<OrderResponse>> save(
            @RequestBody @Valid OrderRequest orderRequest
    ) {
        OrderResponse order = orderService.save(orderRequest);

        return APIResponse.of(
                "Order with ID " + order.getId() + " was created",
                ORDER_API_PATH,
                HttpStatus.CREATED,
                order
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<OrderResponse>>> findAll(Pageable pageable) {
        PageResponse<OrderResponse> orders = orderService.findAll(pageable);

        return APIResponse.of(
                "All Orders: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize(),
                ORDER_API_PATH,
                HttpStatus.OK,
                orders
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<OrderResponse>> findById(@PathVariable @NotBlank Long id) {
        OrderResponse order = orderService.findById(id);

        return APIResponse.of(
                "Order with ID " + order.getId() + " was found",
                ORDER_API_PATH + "/" + id,
                HttpStatus.OK,
                order
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteById(@PathVariable @NotBlank Long id) {
        orderService.deleteById(id);

        return APIResponse.of(
                "Order with ID " + id + " was deleted",
                ORDER_API_PATH + "/" + id,
                HttpStatus.OK,
                null
        );
    }
}
