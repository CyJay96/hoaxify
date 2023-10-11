package com.hoaxify.orderservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsResponse {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "skuCode")
    private String skuCode;

    @JsonProperty(value = "price")
    private BigDecimal price;

    @JsonProperty(value = "quantity")
    private Integer quantity;
}
