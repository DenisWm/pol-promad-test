package com.pol.promad.test.domain.legalprocess;

import com.pol.promad.test.domain.validation.Error;
import com.pol.promad.test.domain.validation.ValidationHandler;
import com.pol.promad.test.domain.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class LegalProcessValidator extends Validator {

    public static final String O_STATUS_DO_PROCESSO_NAO_PODE_SER_NULO = 
            "O status do processo não pode ser nulo";
    public static final String O_STATUS_DO_PROCESSO_NAO_PODE_SER_VAZIO = 
            "O status do processo não pode ser vazio";
    public static final String O_STATUS_DO_PROCESSO_NAO_E_VÁLIDO = 
            "O status do processo não é válido";
    public static final String O_NUMERO_DO_PROCESSO_NAO_PODE_SER_NULO = 
            "O número do processo não pode ser nulo";
    public static final String O_NUMERO_DO_PROCESSO_NAO_PODE_SER_VAZIO = 
            "O número do processo não pode ser vazio";
    public static final String O_NUMERO_DO_PROCESSO_NAO_PODE_TER_MAIS_DE_25_CARACTERES = 
            "O número do processo não pode ter mais de 25 caracteres";
    public static final String D_7_D_2_D_4_D_D_2_D_4 = "\\d{7}-\\d{2}\\.\\d{4}\\.\\d\\.\\d{2}\\.\\d{4}";
    public static final String O_NUMERO_DO_PROCESSO_NAO_RESPEITA_O_FORMATO_CORRETO_NNNNNNN_DD_AAAA_J_TR_OOOO = 
            "O número do processo não respeita o formato correto 'NNNNNNN-DD.AAAA.J.TR.OOOO'";
    public static final List<String> STATUS = 
            List.of("EM_ANDAMENTO", "SUSPENSO", "FINALIZADO", "ARQUIVADO");
    private final LegalProcess legalProcess;
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
            getHandler().append(Error.with(O_STATUS_DO_PROCESSO_NAO_PODE_SER_NULO));
            return;
        }
        if (this.legalProcess.getStatus().getValue().isBlank()) {
            getHandler().append(Error.with(O_STATUS_DO_PROCESSO_NAO_PODE_SER_VAZIO));
            return;
        }
        if(!STATUS.contains(this.legalProcess.getStatus().getValue())){
            getHandler().append(Error.with(O_STATUS_DO_PROCESSO_NAO_E_VÁLIDO));
        }
    }

    private void checkNumber() {
        if (this.legalProcess.getNumber() == null) {
            getHandler().append(Error.with(O_NUMERO_DO_PROCESSO_NAO_PODE_SER_NULO));
            return;
        }
        if (this.legalProcess.getNumber().isBlank()) {
            getHandler().append(Error.with(O_NUMERO_DO_PROCESSO_NAO_PODE_SER_VAZIO));
            return;
        }
        if (this.legalProcess.getNumber().length() > 25) {
            getHandler().append(Error.with(O_NUMERO_DO_PROCESSO_NAO_PODE_TER_MAIS_DE_25_CARACTERES));
            return;
        }
        if (!this.legalProcess.getNumber().matches(D_7_D_2_D_4_D_D_2_D_4)) {
            getHandler().append(Error.with(
                    O_NUMERO_DO_PROCESSO_NAO_RESPEITA_O_FORMATO_CORRETO_NNNNNNN_DD_AAAA_J_TR_OOOO));
        }
    }

    private void checkStatusTransition() {
        if (this.legalProcess.getStatus() != null && this.legalProcess.getPendingStatus() != null) {
            String currentStatus = this.legalProcess.getStatus().getValue();
            String newStatus = this.legalProcess.getPendingStatus().getValue();
            if (this.legalProcess.getPendingStatus().getValue().equals("error")) {
                getHandler().append(Error.with("O novo status do processo não pode ser nulo"));
                return;
            }
            if (this.legalProcess.getPendingStatus().getValue().isBlank()) {
                getHandler().append(Error.with("O novo status do processo não pode ser vazio"));
                return;
            }
            if(!STATUS.contains(this.legalProcess.getPendingStatus().getValue())){
                getHandler().append(Error.with("O novo status do processo não é válido"));
                return;
            }
            if (!this.canTransition(currentStatus, newStatus)) {
                getHandler()
                        .append(Error.with("Transição de status inválida de " + currentStatus + " para " + newStatus));
            }
        }
    }

    private static boolean canTransition(String currentStatus, String newStatus) {
        return ALLOWED_TRANSITIONS.getOrDefault(currentStatus, Set.of()).contains(newStatus);
    }
}
