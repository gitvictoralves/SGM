package com.sgm.sgm.service;

import com.sgm.sgm.dto.NovaOrdemRequest;
import com.sgm.sgm.exception.RegraNegocioException;
import com.sgm.sgm.model.Equipamento;
import com.sgm.sgm.model.OrdemManutencao;
import com.sgm.sgm.model.StatusOrdem;
import com.sgm.sgm.model.Tecnico;
import com.sgm.sgm.repository.EquipamentoRepository;
import com.sgm.sgm.repository.HistoricoStatusRepository;
import com.sgm.sgm.repository.OrdemManutencaoRepository;
import com.sgm.sgm.repository.TecnicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdemManutencaoServiceTest {

    @Mock
    private OrdemManutencaoRepository ordemRepository;

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @Mock
    private TecnicoRepository tecnicoRepository;

    @Mock
    private HistoricoStatusRepository historicoRepository;

    @InjectMocks
    private OrdemManutencaoService ordemManutencaoService;

    private Equipamento equipamento;
    private Tecnico tecnico;
    private NovaOrdemRequest request;

    @BeforeEach
    void setUp() {
        equipamento = new Equipamento();
        equipamento.setId(1L);
        equipamento.setNome("Torno mecânico");
        equipamento.setCodigo("EQP-001");

        tecnico = new Tecnico();
        tecnico.setId(1L);
        tecnico.setNome("João Silva");
        tecnico.setEspecialidade("Mecânica");

        request = new NovaOrdemRequest();
        request.setEquipamentoId(1L);
        request.setTecnicoId(1L);
        request.setDescricaoProblema("Ruído estranho durante a operação");
    }

    @Test
    void abrir_deveLancarRegraNegocioException_quandoJaExisteOrdemAbertaParaOEquipamento() {
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamento));
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico));

        OrdemManutencao ordemJaAberta = new OrdemManutencao();
        ordemJaAberta.setId(10L);
        ordemJaAberta.setStatus(StatusOrdem.ABERTA);

        when(ordemRepository.findByEquipamentoIdAndStatus(1L, StatusOrdem.ABERTA))
                .thenReturn(List.of(ordemJaAberta));

        assertThatThrownBy(() -> ordemManutencaoService.abrir(request))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessageContaining("Já existe uma ordem aberta");

        verify(ordemRepository, never()).save(any(OrdemManutencao.class));
        verify(historicoRepository, never()).save(any());
    }

    @Test
    void abrir_deveCriarOrdemComSucesso_quandoNaoHaOrdemAbertaParaOEquipamento() {
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamento));
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico));
        when(ordemRepository.findByEquipamentoIdAndStatus(1L, StatusOrdem.ABERTA))
                .thenReturn(Collections.emptyList());
        when(ordemRepository.save(any(OrdemManutencao.class))).thenAnswer(invocation -> {
            OrdemManutencao ordem = invocation.getArgument(0);
            ordem.setId(100L);
            return ordem;
        });

        OrdemManutencao resultado = ordemManutencaoService.abrir(request);

        assertThat(resultado.getId()).isEqualTo(100L);
        assertThat(resultado.getStatus()).isEqualTo(StatusOrdem.ABERTA);
        assertThat(resultado.getEquipamento()).isEqualTo(equipamento);
        assertThat(resultado.getTecnico()).isEqualTo(tecnico);
        assertThat(resultado.getDataAbertura()).isNotNull();

        verify(ordemRepository).save(any(OrdemManutencao.class));
        verify(historicoRepository).save(any());
    }

    @Test
    void atualizarStatus_devePreencherDataConclusao_quandoNovoStatusForConcluida() {
        OrdemManutencao ordemExistente = new OrdemManutencao();
        ordemExistente.setId(5L);
        ordemExistente.setStatus(StatusOrdem.EM_ANDAMENTO);

        when(ordemRepository.findById(5L)).thenReturn(Optional.of(ordemExistente));
        when(ordemRepository.save(any(OrdemManutencao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrdemManutencao resultado = ordemManutencaoService.atualizarStatus(5L, StatusOrdem.CONCLUIDA);

        assertThat(resultado.getStatus()).isEqualTo(StatusOrdem.CONCLUIDA);
        assertThat(resultado.getDataConclusao()).isNotNull();

        verify(historicoRepository).save(any());
    }
}