package com.hoaxify.orderservice.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "skuCode")
    private String skuCode;

    @JsonProperty(value = "isInStock")
    private Boolean isInStock;

    @JsonProperty(value = "quantity")
    private Integer quantity;
}
