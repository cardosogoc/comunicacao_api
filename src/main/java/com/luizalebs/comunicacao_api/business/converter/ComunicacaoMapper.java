package com.luizalebs.comunicacao_api.business.converter;

import com.luizalebs.comunicacao_api.business.record.ComunicacaoCancelaRecord;
import com.luizalebs.comunicacao_api.business.record.ComunicacaoRecord;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComunicacaoMapper {

    @Mapping(source = "emailDestinatario", target = "emailDestinatario")
    @Mapping(source = "dataHoraEnvio", target = "dataHoraEnvio")
    ComunicacaoEntity paraComunicacaoEntity(ComunicacaoRecord record);

    ComunicacaoRecord paraComunicacaoRecord(ComunicacaoEntity entity);
    ComunicacaoCancelaRecord paraComunicacaoCancelaRecord(ComunicacaoEntity entity);
}
