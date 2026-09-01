package com.sgm.sgm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NovaOrdemRequest {

    @NotNull(message = "O equipamento é obrigatório")
    private Long equipamentoId;

    @NotNull(message = "O técnico é obrigatório")
    private Long tecnicoId;

    @NotBlank(message = "A descrição do problema é obrigatória")
    private String descricaoProblema;

    public NovaOrdemRequest() {}

    public Long getEquipamentoId() { return equipamentoId; }
    public void setEquipamentoId(Long equipamentoId) { this.equipamentoId = equipamentoId; }

    public Long getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Long tecnicoId) { this.tecnicoId = tecnicoId; }

    public String getDescricaoProblema() { return descricaoProblema; }
    public void setDescricaoProblema(String descricaoProblema) { this.descricaoProblema = descricaoProblema; }
}