package com.pol.promad.test.infrastructure.configuration.usecase;

import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.create.DefaultCreateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.delete.DefaultDeleteLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.delete.DeleteLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.update.DefaultUpdateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.update.UpdateLegalProcessUseCase;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class LegalProcessUseCaseConfig {

    private final LegalProcessGateway legalProcessGateway;

    public LegalProcessUseCaseConfig(final LegalProcessGateway legalProcessGateway) {
        this.legalProcessGateway = Objects.requireNonNull(legalProcessGateway);
    }

    @Bean
    public CreateLegalProcessUseCase createLegalProcessUseCase() {
        return new DefaultCreateLegalProcessUseCase(legalProcessGateway);
    }
    @Bean
    public UpdateLegalProcessUseCase updateLegalProcessUseCase() {
        return new DefaultUpdateLegalProcessUseCase(legalProcessGateway);
    }

    @Bean
    public DeleteLegalProcessUseCase deleteLegalProcessUseCase() {
        return new DefaultDeleteLegalProcessUseCase(legalProcessGateway);
    }
}
