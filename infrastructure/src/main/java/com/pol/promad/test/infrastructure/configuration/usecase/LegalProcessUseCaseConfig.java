package com.pol.promad.test.infrastructure.configuration.usecase;

import com.pol.promad.test.application.legalprocess.create.CreateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.create.DefaultCreateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.delete.DefaultDeleteLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.delete.DeleteLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.get.DefaultGetLegalProcessByIdUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.get.GetLegalProcessByIdUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.list.DefaultListLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.retrieve.list.ListLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.update.DefaultUpdateLegalProcessUseCase;
import com.pol.promad.test.application.legalprocess.update.UpdateLegalProcessUseCase;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import com.pol.promad.test.domain.legalprocess.LegalProcessGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class LegalProcessUseCaseConfig {

    private final LegalProcessGateway legalProcessGateway;

    private final DefendantGateway defendantGateway;

    public LegalProcessUseCaseConfig(
            final LegalProcessGateway legalProcessGateway,
            final DefendantGateway defendantGateway
    ) {
        this.legalProcessGateway = Objects.requireNonNull(legalProcessGateway);
        this.defendantGateway = Objects.requireNonNull(defendantGateway);
    }

    @Bean
    public CreateLegalProcessUseCase createLegalProcessUseCase() {
        return new DefaultCreateLegalProcessUseCase(legalProcessGateway, defendantGateway);
    }
    @Bean
    public UpdateLegalProcessUseCase updateLegalProcessUseCase() {
        return new DefaultUpdateLegalProcessUseCase(legalProcessGateway, defendantGateway);
    }

    @Bean
    public DeleteLegalProcessUseCase deleteLegalProcessUseCase() {
        return new DefaultDeleteLegalProcessUseCase(legalProcessGateway);
    }

    @Bean
    public GetLegalProcessByIdUseCase getLegalProcessByIdUseCase() {
        return new DefaultGetLegalProcessByIdUseCase(legalProcessGateway);
    }

    @Bean
    public ListLegalProcessUseCase listLegalProcessUseCase() {
        return new DefaultListLegalProcessUseCase(legalProcessGateway);
    }
}
