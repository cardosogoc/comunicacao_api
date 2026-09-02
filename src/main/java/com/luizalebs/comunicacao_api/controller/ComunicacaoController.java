package com.luizalebs.comunicacao_api.controller;

import com.luizalebs.comunicacao_api.business.dto.ComunicacaoCancelaRecord;
import com.luizalebs.comunicacao_api.business.dto.ComunicacaoRecord;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comunicacao")
@RequiredArgsConstructor
@Tag(name = "Cuminicacao", description = "API de comunicação")
public class ComunicacaoController {

    private final ComunicacaoService service;

    @PostMapping("/agendar")
    @Operation(summary = "Agenda", description = "Dados de agendamento")
    @ApiResponse(responseCode = "200", description = "Agendamento realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dado(s) informado(s) de maneira incorreta")
    @ApiResponse(responseCode = "409", description = "Já existe uma comunicação para o e-mail informado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    public ResponseEntity<ComunicacaoRecord> agendar(@RequestBody ComunicacaoRecord record)  {
        return ResponseEntity.ok(service.agendarComunicacao(record));
    }

    @GetMapping()
    @Operation(summary = "Busca Status", description = "Busca Status por email")
    @ApiResponse(responseCode = "200", description = "Status encontrado com sucesso")
    @ApiResponse(responseCode = "403", description = "Não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "401", description = "Busca não Autorizada")
    public ResponseEntity<ComunicacaoRecord> buscarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.buscarStatusComunicacao(emailDestinatario));
    }

    @PatchMapping("/cancelar")
    @Operation(summary = "Altera Status ", description = "Altera Status para cancelado")
    @ApiResponse(responseCode = "200", description = "Status alterado para cancelado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Status não encontrado")
    @ApiResponse(responseCode = "401", description = "Não Autorizado")
    public ResponseEntity<ComunicacaoCancelaRecord> cancelarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.alterarStatusComunicacao(emailDestinatario));
    }
}
