package com.hoaxify.orderservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "number")
    private String number;

    @Builder.Default
    @JsonProperty(value = "orderLineItems")
    private List<OrderLineItemsResponse> orderLineItems = new ArrayList<>();
}
