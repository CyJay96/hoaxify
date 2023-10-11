package com.hoaxify.orderservice.mapper;

import com.hoaxify.orderservice.model.dto.request.OrderLineItemsRequest;
import com.hoaxify.orderservice.model.dto.response.OrderLineItemsResponse;
import com.hoaxify.orderservice.model.entity.OrderLineItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderLineItemsMapper {

    @Mapping(target = "id", ignore = true)
    OrderLineItems toOrderLineItems(OrderLineItemsRequest orderLineItemsRequest);

    OrderLineItemsResponse toOrderLineItemsResponse(OrderLineItems orderLineItems);
}
