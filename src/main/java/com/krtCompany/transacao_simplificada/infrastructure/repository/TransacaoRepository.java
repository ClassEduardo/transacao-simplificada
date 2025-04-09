package com.krtCompany.transacao_simplificada.infrastructure.repository;

import com.krtCompany.transacao_simplificada.infrastructure.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
