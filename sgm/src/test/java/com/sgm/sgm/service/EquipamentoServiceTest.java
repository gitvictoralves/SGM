package com.sgm.sgm.service;

import com.sgm.sgm.exception.RecursoNaoEncontradoException;
import com.sgm.sgm.repository.EquipamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private EquipamentoService equipamentoService;

    @Test
    void buscarPorId_deveLancarRecursoNaoEncontradoException_quandoEquipamentoNaoExiste() {
        when(equipamentoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> equipamentoService.buscarPorId(999L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("Equipamento não encontrado");
    }
}