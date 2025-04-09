package com.krtCompany.transacao_simplificada.service;

import com.krtCompany.transacao_simplificada.controller.TransacaoDTO;
import com.krtCompany.transacao_simplificada.infrastructure.entity.TipoUsuario;
import com.krtCompany.transacao_simplificada.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferenciasService {

    private final  UsuarioService usuarioService;

    public void transferirValores(TransacaoDTO transacaoDTO){
        Usuario pagador = usuarioService.buscarUsuario(transacaoDTO.payer());
        Usuario recebedor = usuarioService.buscarUsuario(transacaoDTO.payee());

        validaPagadorLojista(pagador);
        validarSaldoUsuario(pagador, transacaoDTO.value());
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
}
