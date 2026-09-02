package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.business.converter.ComunicacaoMapper;
import com.luizalebs.comunicacao_api.business.record.ComunicacaoCancelaRecord;
import com.luizalebs.comunicacao_api.business.record.ComunicacaoRecord;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ConflictException;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ResourceNotFoundException;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ComunicacaoService {

    private final ComunicacaoRepository repository;
    private final ComunicacaoMapper mapper;
    private final EmailService emailService;


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
        ComunicacaoRecord comunicacaoFinal = mapper.paraComunicacaoRecord(repository.save(entity));

        emailService.enviaEmailComunica(comunicacaoFinal);
        return comunicacaoFinal;
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

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void notificarComunicacoesPendentes() {

        LocalDateTime limite = LocalDateTime.now().minusHours(48);

        List<ComunicacaoEntity> comunicacoes =
                repository.findByStatusEnvioAndDataHoraEnvioBefore(
                        StatusEnvioEnum.PENDENTE,
                        limite
                );

        for (ComunicacaoEntity comunicacao : comunicacoes) {

            ComunicacaoRecord record = mapper.paraComunicacaoRecord(comunicacao);

            if (record.emailDestinatario() == null || record.emailDestinatario().isBlank()) {
                continue;
            }

            emailService.enviaEmailAtraso(record);
        }
    }

}
