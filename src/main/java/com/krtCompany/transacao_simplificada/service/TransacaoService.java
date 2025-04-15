package com.krtCompany.transacao_simplificada.service;

import com.krtCompany.transacao_simplificada.controller.TransacaoDTO;
import com.krtCompany.transacao_simplificada.infrastructure.entity.Carteira;
import com.krtCompany.transacao_simplificada.infrastructure.entity.TipoUsuario;
import com.krtCompany.transacao_simplificada.infrastructure.entity.Transacao;
import com.krtCompany.transacao_simplificada.infrastructure.entity.Usuario;
import com.krtCompany.transacao_simplificada.infrastructure.repository.TransacaoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final UsuarioService usuarioService;
    private final AutorizacaoService autorizacaoService;
    private final CarteiraService carteiraService;
    private final TransacaoRepository repository;
    private final NotificacaoService notificacaoService;

    @Transactional
    public void transferirValores(TransacaoDTO transacaoDTO) throws BadRequestException{
        Usuario pagador = usuarioService.buscarUsuario(transacaoDTO.payer());
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee());

        validaPagadorLojista(pagador);
        validarSaldoUsuario(pagador, transacaoDTO.value());
        validarTransferencia();

        pagador.getCarteira().setSaldo(pagador.getCarteira().getSaldo().subtract(transacaoDTO.value()));
        atualizarSaldoCarteira(pagador.getCarteira());
        recebedor.getCarteira().setSaldo(pagador.getCarteira().getSaldo().add(transacaoDTO.value()));
        atualizarSaldoCarteira(recebedor.getCarteira());

        Transacao transacao = Transacao.builder()
                .valor(transacaoDTO.value())
                .pagador(pagador)
                .recebedor(recebedor)
                .build();

        repository.save(transacao);
        enviarNotificacao();
    }

    private void validaPagadorLojista(Usuario pagador) {
        try {
            if(pagador.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
                throw new IllegalArgumentException("Transação não autorizada para esse tipo de usuário.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarSaldoUsuario(Usuario pagador, BigDecimal valor) {
        try {
            if(pagador.getCarteira().getSaldo().compareTo(valor) < 0) {
                throw new IllegalArgumentException("Transação não autorizada, saldo insuficiente.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void validarTransferencia() {
        try {
            if(!autorizacaoService.validarTransferencia()) {
                throw new IllegalArgumentException("Transação não autorizada pela api.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void atualizarSaldoCarteira(Carteira carteira) {
        carteiraService.salvar(carteira);
    }

    private void enviarNotificacao() throws BadRequestException{
        try {
            notificacaoService.enviarNotificacao();
        } catch (HttpClientErrorException e) {
            throw new BadRequestException("Erro ao enviar notificacao");
        }
    }
}
