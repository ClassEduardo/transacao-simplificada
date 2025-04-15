package com.krtCompany.transacao_simplificada.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "https://util.devi.tools/api/v2/notify", name = "notificacao")
public interface NotificacaoClient {

    @PostMapping
    void enviarNotificacao();
}
