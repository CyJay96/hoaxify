package com.hoaxify.inventoryservice.controller;

import com.hoaxify.inventoryservice.model.dto.request.InventoryRequest;
import com.hoaxify.inventoryservice.model.dto.response.APIResponse;
import com.hoaxify.inventoryservice.model.dto.response.InventoryResponse;
import com.hoaxify.inventoryservice.model.dto.response.PageResponse;
import com.hoaxify.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(InventoryController.INVENTORY_API_PATH)
@RequiredArgsConstructor
public class InventoryController {

    public static final String INVENTORY_API_PATH = "/api/v0/inventories";

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<APIResponse<InventoryResponse>> save(
            @RequestBody @Valid InventoryRequest inventoryRequest
    ) {
        InventoryResponse inventory = inventoryService.save(inventoryRequest);

        return APIResponse.of(
                "Inventory with ID " + inventory.getId() + " was created",
                INVENTORY_API_PATH,
                HttpStatus.CREATED,
                inventory
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<PageResponse<InventoryResponse>>> findAll(Pageable pageable) {
        PageResponse<InventoryResponse> inventories = inventoryService.findAll(pageable);

        return APIResponse.of(
                "All Inventories: page_number: " + pageable.getPageNumber() +
                        "; page_size: " + pageable.getPageSize(),
                INVENTORY_API_PATH,
                HttpStatus.OK,
                inventories
        );
    }

    @GetMapping("byId/{id}")
    public ResponseEntity<APIResponse<InventoryResponse>> findById(@PathVariable @NotNull @PositiveOrZero Long id) {
        InventoryResponse inventory = inventoryService.findById(id);

        return APIResponse.of(
                "Inventory with ID " + inventory.getId() + " was found",
                INVENTORY_API_PATH + "/" + id,
                HttpStatus.OK,
                inventory
        );
    }

    @GetMapping("bySkuCode/{skuCode}")
    public ResponseEntity<APIResponse<Boolean>> existsBySkuCode(@PathVariable @NotBlank String skuCode) {
        Boolean isExistsBySkuCode = inventoryService.existsBySkuCode(skuCode);

        return APIResponse.of(
                "Inventory with skuCode: " + skuCode + " was found",
                INVENTORY_API_PATH + "/" + skuCode,
                HttpStatus.OK,
                isExistsBySkuCode
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<InventoryResponse>> update(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody @Valid InventoryRequest inventoryRequest
    ) {
        InventoryResponse inventory = inventoryService.update(id, inventoryRequest);

        return APIResponse.of(
                "Changes were applied to the Inventory with ID " + id,
                INVENTORY_API_PATH + "/" + id,
                HttpStatus.OK,
                inventory
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<APIResponse<InventoryResponse>> updatePartially(
            @PathVariable @NotNull @PositiveOrZero Long id,
            @RequestBody InventoryRequest inventoryRequest
    ) {
        InventoryResponse inventory = inventoryService.update(id, inventoryRequest);

        return APIResponse.of(
                "Partial changes were applied to the Inventory with ID " + id,
                INVENTORY_API_PATH + "/" + id,
                HttpStatus.OK,
                inventory
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteById(@PathVariable @NotNull @PositiveOrZero Long id) {
        inventoryService.deleteById(id);

        return APIResponse.of(
                "Inventory with ID " + id + " was deleted",
                INVENTORY_API_PATH + "/" + id,
                HttpStatus.OK,
                null
        );
    }
}
