package com.sgm.sgm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_status")
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ordem_id", nullable = false)
    private OrdemManutencao ordem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrdem statusAnterior;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOrdem statusNovo;

    @Column(nullable = false)
    private LocalDateTime dataAlteracao;

    public HistoricoStatus() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OrdemManutencao getOrdem() { return ordem; }
    public void setOrdem(OrdemManutencao ordem) { this.ordem = ordem; }

    public StatusOrdem getStatusAnterior() { return statusAnterior; }
    public void setStatusAnterior(StatusOrdem statusAnterior) { this.statusAnterior = statusAnterior; }

    public StatusOrdem getStatusNovo() { return statusNovo; }
    public void setStatusNovo(StatusOrdem statusNovo) { this.statusNovo = statusNovo; }

    public LocalDateTime getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(LocalDateTime dataAlteracao) { this.dataAlteracao = dataAlteracao; }
}