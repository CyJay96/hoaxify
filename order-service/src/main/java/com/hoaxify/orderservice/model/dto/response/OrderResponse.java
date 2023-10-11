package com.hoaxify.orderservice.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private Long id;

    private String number;

    @Builder.Default
    private List<OrderLineItemsResponse> orderLineItems = new ArrayList<>();
}
