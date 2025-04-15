package com.krtCompany.transacao_simplificada.service;

import org.springframework.stereotype.Service;

import com.krtCompany.transacao_simplificada.infrastructure.entity.Carteira;
import com.krtCompany.transacao_simplificada.infrastructure.repository.CarteiraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarteiraService {

    private final CarteiraRepository repository;

    public void salvar(Carteira carteira) {
        repository.save(carteira);
    }

}
