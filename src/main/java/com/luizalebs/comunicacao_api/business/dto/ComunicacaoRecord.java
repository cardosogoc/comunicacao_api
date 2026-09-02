package com.luizalebs.comunicacao_api.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;

import java.time.LocalDateTime;

public record ComunicacaoRecord(@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                                LocalDateTime dataHoraEnvio,
                                String nomeDestinatario,
                                String emailDestinatario,
                                String telefoneDestinatario,
                                String mensagem,
                                ModoEnvioEnum modoDeEnvio,
                                @JsonIgnore
                                StatusEnvioEnum statusEnvio) {
}
