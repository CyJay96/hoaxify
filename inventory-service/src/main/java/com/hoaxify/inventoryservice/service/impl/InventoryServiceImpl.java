package com.hoaxify.inventoryservice.service.impl;

import com.hoaxify.inventoryservice.exception.EntityNotFoundException;
import com.hoaxify.inventoryservice.mapper.InventoryMapper;
import com.hoaxify.inventoryservice.model.dto.request.InventoryRequest;
import com.hoaxify.inventoryservice.model.dto.response.InventoryResponse;
import com.hoaxify.inventoryservice.model.dto.response.PageResponse;
import com.hoaxify.inventoryservice.model.entity.Inventory;
import com.hoaxify.inventoryservice.repository.InventoryRepository;
import com.hoaxify.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse save(InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository.save(inventoryMapper.toInventory(inventoryRequest));
        return inventoryMapper.toInventoryResponse(inventory);
    }

    @Override
    public PageResponse<InventoryResponse> findAll(Pageable pageable) {
        Page<Inventory> inventoryPage = inventoryRepository.findAll(pageable);

        List<InventoryResponse> inventories = inventoryPage.stream()
                .map(inventoryMapper::toInventoryResponse)
                .toList();

        return PageResponse.<InventoryResponse>builder()
                .number(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .numberOfElements(inventories.size())
                .content(inventories)
                .build();
    }

    @Override
    public InventoryResponse findById(Long id) {
        return inventoryRepository.findById(id)
                .map(inventoryMapper::toInventoryResponse)
                .orElseThrow(() -> new EntityNotFoundException(Inventory.class, id));
    }

    @Override
    public List<InventoryResponse> findBySkuCodes(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventoryMapper::toInventoryResponse)
                .toList();
    }

    @Override
    public InventoryResponse update(Long id, InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Inventory.class, id));
        inventoryMapper.updateInventory(inventoryRequest, inventory);
        return inventoryMapper.toInventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    public void deleteById(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new EntityNotFoundException(Inventory.class, id);
        }
        inventoryRepository.deleteById(id);
    }
}
