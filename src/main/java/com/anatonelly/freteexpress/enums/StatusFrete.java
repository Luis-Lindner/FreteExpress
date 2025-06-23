package com.anatonelly.freteexpress.enums;

public enum StatusFrete {
    PENDENTE,   // Disponível para todos os motoristas
    SOLICITADO, // Um motorista demonstrou interesse, aguardando aprovação da empresa
    ACEITO,     // Empresa aprovou, frete em andamento com um motorista
    FINALIZADO, // Frete concluído com sucesso
    CANCELADO   // Frete foi cancelado
}