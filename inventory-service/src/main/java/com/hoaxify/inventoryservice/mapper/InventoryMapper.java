package com.hoaxify.inventoryservice.mapper;

import com.hoaxify.inventoryservice.model.dto.request.InventoryRequest;
import com.hoaxify.inventoryservice.model.dto.response.InventoryResponse;
import com.hoaxify.inventoryservice.model.entity.Inventory;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    Inventory toInventory(InventoryRequest inventoryRequest);

    @Mapping(target = "isInStock", expression = "java(inventory.getQuantity() > 0)")
    InventoryResponse toInventoryResponse(Inventory inventory);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    @Mapping(target = "id", ignore = true)
    void updateInventory(InventoryRequest inventoryRequest, @MappingTarget Inventory inventory);
}
