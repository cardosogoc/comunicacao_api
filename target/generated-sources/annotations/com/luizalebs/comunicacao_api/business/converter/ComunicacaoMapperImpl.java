package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.business.record.ComunicacaoCancelaRecord;
import com.luizalebs.comunicacao_api.business.record.ComunicacaoRecord;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-02T08:58:23-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 25.0.4.1 (Oracle Corporation)"
)
@Component
public class ComunicacaoMapperImpl implements ComunicacaoMapper {

    @Override
    public ComunicacaoEntity paraComunicacaoEntity(ComunicacaoRecord record) {
        if ( record == null ) {
            return null;
        }

        ComunicacaoEntity.ComunicacaoEntityBuilder comunicacaoEntity = ComunicacaoEntity.builder();

        comunicacaoEntity.emailDestinatario( record.emailDestinatario() );
        comunicacaoEntity.dataHoraEnvio( record.dataHoraEnvio() );
        comunicacaoEntity.nomeDestinatario( record.nomeDestinatario() );
        comunicacaoEntity.telefoneDestinatario( record.telefoneDestinatario() );
        comunicacaoEntity.mensagem( record.mensagem() );
        comunicacaoEntity.modoDeEnvio( record.modoDeEnvio() );
        comunicacaoEntity.statusEnvio( record.statusEnvio() );

        return comunicacaoEntity.build();
    }

    @Override
    public ComunicacaoRecord paraComunicacaoRecord(ComunicacaoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        LocalDateTime dataHoraEnvio = null;
        String nomeDestinatario = null;
        String emailDestinatario = null;
        String telefoneDestinatario = null;
        String mensagem = null;
        ModoEnvioEnum modoDeEnvio = null;
        StatusEnvioEnum statusEnvio = null;

        dataHoraEnvio = entity.getDataHoraEnvio();
        nomeDestinatario = entity.getNomeDestinatario();
        emailDestinatario = entity.getEmailDestinatario();
        telefoneDestinatario = entity.getTelefoneDestinatario();
        mensagem = entity.getMensagem();
        modoDeEnvio = entity.getModoDeEnvio();
        statusEnvio = entity.getStatusEnvio();

        ComunicacaoRecord comunicacaoRecord = new ComunicacaoRecord( dataHoraEnvio, nomeDestinatario, emailDestinatario, telefoneDestinatario, mensagem, modoDeEnvio, statusEnvio );

        return comunicacaoRecord;
    }

    @Override
    public ComunicacaoCancelaRecord paraComunicacaoCancelaRecord(ComunicacaoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        String mensagem = null;
        StatusEnvioEnum statusEnvio = null;

        mensagem = entity.getMensagem();
        statusEnvio = entity.getStatusEnvio();

        ComunicacaoCancelaRecord comunicacaoCancelaRecord = new ComunicacaoCancelaRecord( mensagem, statusEnvio );

        return comunicacaoCancelaRecord;
    }
}
