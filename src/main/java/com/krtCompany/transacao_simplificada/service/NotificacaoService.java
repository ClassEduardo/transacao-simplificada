package com.krtCompany.transacao_simplificada.service;

import org.springframework.stereotype.Service;

import com.krtCompany.transacao_simplificada.infrastructure.clients.NotificacaoClient;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoClient client;

    public void enviarNotificacao() {
        client.enviarNotificacao();
    }
}
