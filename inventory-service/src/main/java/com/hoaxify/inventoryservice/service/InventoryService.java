package com.hoaxify.inventoryservice.service;

import com.hoaxify.inventoryservice.model.dto.request.InventoryRequest;
import com.hoaxify.inventoryservice.model.dto.response.InventoryResponse;
import com.hoaxify.inventoryservice.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {

    InventoryResponse save(InventoryRequest inventoryRequest);

    PageResponse<InventoryResponse> findAll(Pageable pageable);

    InventoryResponse findById(Long id);

    List<InventoryResponse> findBySkuCodes(List<String> skuCodes);

    InventoryResponse update(Long id, InventoryRequest inventoryRequest);

    void deleteById(Long id);
}
