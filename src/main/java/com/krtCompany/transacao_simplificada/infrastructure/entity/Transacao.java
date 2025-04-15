package com.krtCompany.transacao_simplificada.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="transacao")
@Table
@Builder
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private BigDecimal valor;

    @JoinColumn(name = "recebedor_id")
    @OneToOne
    private Usuario recebedor;

    @JoinColumn(name = "pagador_id")
    @OneToOne
    private Usuario pagador;

    private LocalDateTime dataHoraTransacao;

    @PrePersist
    void prePersist() {
        dataHoraTransacao = LocalDateTime.now();
    }
}
