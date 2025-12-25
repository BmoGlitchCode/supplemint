package com.BmoGlitchCode.supplemint.infrastructure.config.inventory;

import com.BmoGlitchCode.supplemint.application.usecase.inventory.*;
import com.BmoGlitchCode.supplemint.domain.port.input.inventory.*;
import com.BmoGlitchCode.supplemint.domain.port.output.SupplementInventoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for wiring up inventory-related use cases with their
 * dependencies.
 */
@Configuration
public class InventoryUseCaseConfig {

    @Bean
    public AddInventoryUseCase addInventoryUseCase(SupplementInventoryRepository inventoryRepository) {
        return new AddInventoryUseCaseImpl(inventoryRepository);
    }

    @Bean
    public UpdateInventoryUseCase updateInventoryUseCase(SupplementInventoryRepository inventoryRepository) {
        return new UpdateInventoryUseCaseImpl(inventoryRepository);
    }

    @Bean
    public GetInventoryUseCase getInventoryUseCase(SupplementInventoryRepository inventoryRepository) {
        return new GetInventoryUseCaseImpl(inventoryRepository);
    }

    @Bean
    public ListUserInventoryUseCase listUserInventoryUseCase(SupplementInventoryRepository inventoryRepository) {
        return new ListUserInventoryUseCaseImpl(inventoryRepository);
    }

    @Bean
    public DeleteInventoryUseCase deleteInventoryUseCase(SupplementInventoryRepository inventoryRepository) {
        return new DeleteInventoryUseCaseImpl(inventoryRepository);
    }
}
