package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.business.converter.ComunicacaoMapper;
import com.luizalebs.comunicacao_api.business.dto.ComunicacaoCancelaRecord;
import com.luizalebs.comunicacao_api.business.dto.ComunicacaoRecord;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ConflictException;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ResourceNotFoundException;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ComunicacaoService {

    private final ComunicacaoRepository repository;
    private final ComunicacaoMapper mapper;


    public ComunicacaoRecord agendarComunicacao(ComunicacaoRecord record) {

        if (record.emailDestinatario() == null || record.emailDestinatario().isBlank()) {
            throw new IllegalArgumentException("E-mail do destinatário é obrigatório");
        }

        if (repository.findByEmailDestinatario(record.emailDestinatario()) != null) {
            throw new ConflictException("Já existe uma comunicação para o e-mail informado");
        }

        ComunicacaoRecord recordFinal = new ComunicacaoRecord(
                LocalDateTime.now(), record.nomeDestinatario(), record.emailDestinatario(), record.telefoneDestinatario(),
                record.mensagem(), record.modoDeEnvio(), StatusEnvioEnum.PENDENTE);

        ComunicacaoEntity entity = mapper.paraComunicacaoEntity(recordFinal);

        return mapper.paraComunicacaoRecord(repository.save(entity));
    }

    public ComunicacaoRecord buscarStatusComunicacao(String emailDestinatario) {
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity)) {
            throw new ResourceNotFoundException( "Email não encontrado: " + emailDestinatario);
        }
        return mapper.paraComunicacaoRecord(entity);
    }

    public ComunicacaoCancelaRecord alterarStatusComunicacao(String emailDestinatario) {
        ComunicacaoEntity entity = repository.findByEmailDestinatario(emailDestinatario);
        if (Objects.isNull(entity)) {
            throw new ResourceNotFoundException(
                    "Email não encontrado: " + emailDestinatario
            );
        }
        entity.setStatusEnvio(StatusEnvioEnum.CANCELADO);
        repository.save(entity);

        return mapper.paraComunicacaoCancelaRecord(entity);
    }

}
