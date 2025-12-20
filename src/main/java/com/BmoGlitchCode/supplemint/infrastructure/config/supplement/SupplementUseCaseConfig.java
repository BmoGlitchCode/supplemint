package com.BmoGlitchCode.supplemint.infrastructure.config.supplement;

import com.BmoGlitchCode.supplemint.application.usecase.supplement.*;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.*;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplementUseCaseConfig {

    @Bean
    public CreateSupplementUseCase createSupplementUseCase(SupplementRepository supplementRepository) {
        return new CreateSupplementUseCaseImpl(supplementRepository);
    }

    @Bean
    public UpdateSupplementUseCase updateSupplementUseCase(SupplementRepository supplementRepository) {
        return new UpdateSupplementUseCaseImpl(supplementRepository);
    }

    @Bean
    public GetSupplementUseCase getSupplementUseCase(SupplementRepository supplementRepository) {
        return new GetSupplementUseCaseImpl(supplementRepository);
    }

    @Bean
    public ListUserSupplementsUseCase listUserSupplementsUseCase(SupplementRepository supplementRepository) {
        return new ListUserSupplementsUseCaseImpl(supplementRepository);
    }

    @Bean
    public DeleteSupplementUseCase deleteSupplementUseCase(SupplementRepository supplementRepository) {
        return new DeleteSupplementUseCaseImpl(supplementRepository);
    }
}
