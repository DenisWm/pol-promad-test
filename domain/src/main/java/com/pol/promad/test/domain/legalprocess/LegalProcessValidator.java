package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.validation.Error;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LegalProcessValidator extends Validator {

    private final LegalProcess legalProcess;
    public static final List<String> STATUS = List.of("EM_ANDAMENTO", "SUSPENSO", "FINALIZADO", "ARQUIVADO");

    private static final Map<String, Set<String>> ALLOWED_TRANSITIONS = Map.of(
            "EM_ANDAMENTO", Set.of("SUSPENSO", "FINALIZADO"),
            "SUSPENSO", Set.of("EM_ANDAMENTO", "ARQUIVADO"),
            "FINALIZADO", Set.of("ARQUIVADO"),
            "ARQUIVADO", Set.of()
    );


    public LegalProcessValidator(final ValidationHandler aHandler, final LegalProcess legalProcess) {
        super(aHandler);
        this.legalProcess = legalProcess;
    }

    @Override
    public void validate() {
        checkNumber();
        checkStatus();
        checkStatusTransition();
    }

    private void checkStatus() {
        if (this.legalProcess.getStatus().getValue().equals("error")) {
            getHandler().append(new Error("O status do processo não pode ser nulo"));
            return;
        }
        if (this.legalProcess.getStatus().getValue().isBlank()) {
            getHandler().append(new Error("O status do processo não pode ser vazio"));
            return;
        }
        if(!STATUS.contains(this.legalProcess.getStatus().getValue())){
            getHandler().append(new Error("O status do processo não é válido"));
        }
    }

    private void checkNumber() {
        if (this.legalProcess.getNumber() == null) {
            getHandler().append(new Error("O número do processo não pode ser nulo"));
            return;
        }
        if (this.legalProcess.getNumber().isBlank()) {
            getHandler().append(new Error("O número do processo não pode ser vazio"));
            return;
        }
        if (this.legalProcess.getNumber().length() > 25) {
            getHandler().append(new Error("O número do processo não pode ter mais de 25 caracteres"));
            return;
        }
        if (!this.legalProcess.getNumber().matches("\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}")) {
            getHandler().append(new Error("O número do processo não respeita o formato correto 'NNNNNNN-DD.AAAA.J.TR.OOOO'"));
        }
    }

    private void checkStatusTransition() {
        if (this.legalProcess.getStatus() != null && this.legalProcess.getPendingStatus() != null) {
            String currentStatus = this.legalProcess.getStatus().getValue();
            String newStatus = this.legalProcess.getPendingStatus().getValue();
            if (this.legalProcess.getPendingStatus().getValue().equals("error")) {
                getHandler().append(new Error("O novo status do processo não pode ser nulo"));
                return;
            }
            if (this.legalProcess.getPendingStatus().getValue().isBlank()) {
                getHandler().append(new Error("O novo status do processo não pode ser vazio"));
                return;
            }
            if(!STATUS.contains(this.legalProcess.getPendingStatus().getValue())){
                getHandler().append(new Error("O novo status do processo não é válido"));
                return;
            }
            if (!this.canTransition(currentStatus, newStatus)) {
                getHandler().append(new Error("Transição de status inválida de " + currentStatus + " para " + newStatus));
            }
        }
    }

    public static boolean canTransition(String currentStatus, String newStatus) {
        return ALLOWED_TRANSITIONS.getOrDefault(currentStatus, Set.of()).contains(newStatus);
    }


}
