package com.hoaxify.orderservice.model.dto.request;

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
public class OrderRequest {

    @Builder.Default
    private List<OrderLineItemsRequest> orderLineItems = new ArrayList<>();
}
