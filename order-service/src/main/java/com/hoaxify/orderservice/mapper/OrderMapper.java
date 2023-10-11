package com.hoaxify.orderservice.mapper;

import com.hoaxify.orderservice.model.dto.request.OrderRequest;
import com.hoaxify.orderservice.model.dto.response.OrderResponse;
import com.hoaxify.orderservice.model.entity.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        uses = OrderLineItemsMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "number", expression = "java(java.util.UUID.randomUUID().toString())")
    Order toOrder(OrderRequest orderRequest);

    OrderResponse toOrderResponse(Order order);
}
