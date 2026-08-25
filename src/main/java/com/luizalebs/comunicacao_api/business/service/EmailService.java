package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.business.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.infraestructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient client;

    public void enviarEmail(@RequestBody ComunicacaoOutDTO dto){
        client.enviarEmail(dto);
    }
}
