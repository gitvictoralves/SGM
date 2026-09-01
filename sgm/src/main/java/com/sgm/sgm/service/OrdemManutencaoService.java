package com.sgm.sgm.service;

import com.sgm.sgm.dto.NovaOrdemRequest;
import com.sgm.sgm.model.*;
import com.sgm.sgm.repository.EquipamentoRepository;
import com.sgm.sgm.repository.HistoricoStatusRepository;
import com.sgm.sgm.repository.OrdemManutencaoRepository;
import com.sgm.sgm.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdemManutencaoService {

    @Autowired
    private OrdemManutencaoRepository ordemRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private HistoricoStatusRepository historicoRepository;

    public List<OrdemManutencao> listarTodos() {
        return ordemRepository.findAll();
    }

    public OrdemManutencao buscarPorId(Long id) {
        return ordemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordem de manutenção não encontrada"));
    }

    public OrdemManutencao abrir(NovaOrdemRequest request) {
        Equipamento equipamento = equipamentoRepository.findById(request.getEquipamentoId())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        Tecnico tecnico = tecnicoRepository.findById(request.getTecnicoId())
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

        // Regra de negócio: não permitir ordem duplicada para o mesmo equipamento
        List<OrdemManutencao> ordensAbertas = ordemRepository
                .findByEquipamentoIdAndStatus(equipamento.getId(), StatusOrdem.ABERTA);

        if (!ordensAbertas.isEmpty()) {
            throw new IllegalStateException("Já existe uma ordem aberta para este equipamento");
        }

        OrdemManutencao ordem = new OrdemManutencao();
        ordem.setEquipamento(equipamento);
        ordem.setTecnico(tecnico);
        ordem.setDescricaoProblema(request.getDescricaoProblema());
        ordem.setStatus(StatusOrdem.ABERTA);
        ordem.setDataAbertura(LocalDateTime.now());

        OrdemManutencao ordemSalva = ordemRepository.save(ordem);

        registrarHistorico(ordemSalva, null, StatusOrdem.ABERTA);

        return ordemSalva;
    }

    public OrdemManutencao atualizarStatus(Long id, StatusOrdem novoStatus) {
        OrdemManutencao ordem = buscarPorId(id);
        StatusOrdem statusAnterior = ordem.getStatus();

        ordem.setStatus(novoStatus);

        if (novoStatus == StatusOrdem.CONCLUIDA) {
            ordem.setDataConclusao(LocalDateTime.now());
        }

        OrdemManutencao ordemAtualizada = ordemRepository.save(ordem);

        registrarHistorico(ordemAtualizada, statusAnterior, novoStatus);

        return ordemAtualizada;
    }

    private void registrarHistorico(OrdemManutencao ordem, StatusOrdem statusAnterior, StatusOrdem statusNovo) {
        HistoricoStatus historico = new HistoricoStatus();
        historico.setOrdem(ordem);
        historico.setStatusAnterior(statusAnterior);
        historico.setStatusNovo(statusNovo);
        historico.setDataAlteracao(LocalDateTime.now());
        historicoRepository.save(historico);
    }

    public List<HistoricoStatus> listarHistorico(Long ordemId) {
        return historicoRepository.findAll().stream()
                .filter(h -> h.getOrdem().getId().equals(ordemId))
                .toList();
    }
}