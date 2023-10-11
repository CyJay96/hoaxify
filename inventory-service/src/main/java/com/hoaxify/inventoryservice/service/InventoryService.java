package com.hoaxify.inventoryservice.service;

import com.hoaxify.inventoryservice.model.dto.request.InventoryRequest;
import com.hoaxify.inventoryservice.model.dto.response.InventoryResponse;
import com.hoaxify.inventoryservice.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    InventoryResponse save(InventoryRequest inventoryRequest);

    PageResponse<InventoryResponse> findAll(Pageable pageable);

    InventoryResponse findById(Long id);

    Boolean existsBySkuCode(String skuCode);

    InventoryResponse update(Long id, InventoryRequest inventoryRequest);

    void deleteById(Long id);
}
