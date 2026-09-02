package com.luizalebs.comunicacao_api.business.record;

import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;

public record ComunicacaoCancelaRecord(String mensagem,
                                       StatusEnvioEnum statusEnvio) {
}
