package com.luizalebs.comunicacao_api.business.service;

import com.luizalebs.comunicacao_api.business.record.ComunicacaoRecord;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente}")
    public String remetente;

    @Value("${envio.email.nomeRemetente}")
    public String nomeRemetente;

    public void enviaEmailComunica(ComunicacaoRecord record) {


        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMessageHelper.setTo(InternetAddress.parse(record.emailDestinatario()));
            mimeMessageHelper.setSubject("Notificação de Tarefa");

            Context context = new Context();
            context.setVariable("nomeTarefa", record.nomeDestinatario());
            context.setVariable("dataEvento", record.dataHoraEnvio());
            context.setVariable("descricao", record.mensagem());

            String template = templateEngine.process("notificacao", context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(mensagem);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao Enviar o Email", e.getCause());
        }
    }

    public void enviaEmailAtraso(ComunicacaoRecord record) {

        try {
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mensagem, true, StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress(remetente, nomeRemetente));
            helper.setTo(InternetAddress.parse(record.emailDestinatario()));
            helper.setSubject("Tarefa em atraso");

            Context context = new Context();
            context.setVariable("nomeTarefa", record.nomeDestinatario());
            context.setVariable("dataEvento", record.dataHoraEnvio());
            context.setVariable("descricao", record.mensagem());

            String template = templateEngine.process("notificacao-atraso", context);

            helper.setText(template, true);

            javaMailSender.send(mensagem);

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailException("Erro ao enviar e-mail de atraso", e.getCause());
        }
    }
}
