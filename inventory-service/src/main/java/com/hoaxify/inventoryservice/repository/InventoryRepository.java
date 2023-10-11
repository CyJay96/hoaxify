package com.hoaxify.inventoryservice.repository;

import com.hoaxify.inventoryservice.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Boolean existsBySkuCode(String skuCode);
}
