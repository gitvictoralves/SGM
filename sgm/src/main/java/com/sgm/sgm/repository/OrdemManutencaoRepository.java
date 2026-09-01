package com.sgm.sgm.repository;

import com.sgm.sgm.model.OrdemManutencao;
import com.sgm.sgm.model.StatusOrdem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemManutencaoRepository extends JpaRepository<OrdemManutencao, Long> {

    List<OrdemManutencao> findByEquipamentoIdAndStatus(Long equipamentoId, StatusOrdem status);
}