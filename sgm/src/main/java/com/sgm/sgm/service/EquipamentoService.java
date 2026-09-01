package com.sgm.sgm.service;

import com.sgm.sgm.model.Equipamento;
import com.sgm.sgm.repository.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    public Equipamento buscarPorId(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
    }

    public Equipamento salvar(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    public Equipamento atualizar(Long id, Equipamento dadosAtualizados) {
        Equipamento equipamento = buscarPorId(id);
        equipamento.setNome(dadosAtualizados.getNome());
        equipamento.setCodigo(dadosAtualizados.getCodigo());
        equipamento.setDescricao(dadosAtualizados.getDescricao());
        equipamento.setStatus(dadosAtualizados.getStatus());
        return equipamentoRepository.save(equipamento);
    }

    public void deletar(Long id) {
        Equipamento equipamento = buscarPorId(id);
        equipamentoRepository.delete(equipamento);
    }
}