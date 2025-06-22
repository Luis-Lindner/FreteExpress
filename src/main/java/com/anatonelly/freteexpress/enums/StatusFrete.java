// Crie este novo arquivo: src/main/java/com/anatonelly/freteexpress/model/StatusFrete.java
package com.anatonelly.freteexpress.enums;

public enum StatusFrete {
    PENDENTE,      // Aguardando um motorista solicitar
    SOLICITADO,    // Um motorista solicitou, aguardando aprovação da empresa
    EM_ANDAMENTO,  // Frete sendo realizado
    FINALIZADO,    // Frete concluído com sucesso
    CANCELADO      // Frete foi cancelado
}