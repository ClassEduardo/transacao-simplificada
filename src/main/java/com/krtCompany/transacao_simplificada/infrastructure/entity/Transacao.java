package com.krtCompany.transacao_simplificada.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name ="transacao")
@Table
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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
