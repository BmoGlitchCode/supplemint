package com.BmoGlitchCode.supplemint.infrastructure.config.stack;

import com.BmoGlitchCode.supplemint.application.usecase.stack.*;
import com.BmoGlitchCode.supplemint.domain.port.input.stack.*;
import com.BmoGlitchCode.supplemint.domain.port.output.stack.StackRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StackUseCaseConfig {

    @Bean
    public CreateStackUseCase createStackUseCase(StackRepository stackRepository) {
        return new CreateStackUseCaseImpl(stackRepository);
    }

    @Bean
    public UpdateStackUseCase updateStackUseCase(StackRepository stackRepository) {
        return new UpdateStackUseCaseImpl(stackRepository);
    }

    @Bean
    public DeleteStackUseCase deleteStackUseCase(StackRepository stackRepository) {
        return new DeleteStackUseCaseImpl(stackRepository);
    }

    @Bean
    public GetStackUseCase getStackUseCase(StackRepository stackRepository) {
        return new GetStackUseCaseImpl(stackRepository);
    }

    @Bean
    public ListUserStacksUseCase listUserStacksUseCase(StackRepository stackRepository) {
        return new ListUserStacksUseCaseImpl(stackRepository);
    }

    @Bean
    public AddStackItemUseCase addStackItemUseCase(StackRepository stackRepository) {
        return new AddStackItemUseCaseImpl(stackRepository);
    }

    @Bean
    public RemoveStackItemUseCase removeStackItemUseCase(StackRepository stackRepository) {
        return new RemoveStackItemUseCaseImpl(stackRepository);
    }
}
