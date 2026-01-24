package com.BmoGlitchCode.supplemint.infrastructure.config.supplementlog;

import com.BmoGlitchCode.supplemint.application.usecase.supplementlog.*;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.*;
import com.BmoGlitchCode.supplemint.domain.port.output.supplement.SupplementRepository;
import com.BmoGlitchCode.supplemint.domain.port.output.supplementlog.SupplementLogRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplementLogUseCaseConfig {

    @Bean
    public LogSupplementUseCase logSupplementUseCase(
            SupplementLogRepository supplementLogRepository,
            SupplementRepository supplementRepository) {
        return new LogSupplementUseCaseImpl(supplementLogRepository, supplementRepository);
    }

    @Bean
    public GetSupplementLogUseCase getSupplementLogUseCase(SupplementLogRepository repository) {
        return new GetSupplementLogUseCaseImpl(repository);
    }

    @Bean
    public ListSupplementLogsUseCase listSupplementLogsUseCase(SupplementLogRepository repository) {
        return new ListSupplementLogsUseCaseImpl(repository);
    }

    @Bean
    public UpdateSupplementLogUseCase updateSupplementLogUseCase(SupplementLogRepository repository) {
        return new UpdateSupplementLogUseCaseImpl(repository);
    }

    @Bean
    public DeleteSupplementLogUseCase deleteSupplementLogUseCase(
            SupplementLogRepository supplementLogRepository,
            SupplementRepository supplementRepository) {
        return new DeleteSupplementLogUseCaseImpl(supplementLogRepository, supplementRepository);
    }
}
