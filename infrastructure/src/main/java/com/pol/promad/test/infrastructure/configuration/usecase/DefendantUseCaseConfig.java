package com.pol.promad.test.infrastructure.configuration.usecase;

import com.pol.promad.test.application.defendant.create.CreateDefendantUseCase;
import com.pol.promad.test.application.defendant.create.DefaultCreateDefendantUseCase;
import com.pol.promad.test.application.defendant.retrieve.get.DefaultGetDefendantByIdUseCase;
import com.pol.promad.test.application.defendant.retrieve.get.GetDefendantByIdUseCase;
import com.pol.promad.test.application.defendant.retrieve.list.DefaultListDefendantUseCase;
import com.pol.promad.test.application.defendant.retrieve.list.ListDefendantUseCase;
import com.pol.promad.test.domain.defendant.DefendantGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class DefendantUseCaseConfig {

    private final DefendantGateway defendantGateway;

    public DefendantUseCaseConfig(final DefendantGateway defendantGateway) {
        this.defendantGateway = Objects.requireNonNull(defendantGateway);
    }

    @Bean
    public CreateDefendantUseCase createDefendantUseCase() {
        return new DefaultCreateDefendantUseCase(defendantGateway);
    }

    @Bean
    public ListDefendantUseCase listDefendantUseCase() {
        return new DefaultListDefendantUseCase(defendantGateway);
    }

    @Bean
    public GetDefendantByIdUseCase getDefendantUseCase() {
        return new DefaultGetDefendantByIdUseCase(defendantGateway);
    }

}
