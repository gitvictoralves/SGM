package com.sgm.sgm.service;

import com.sgm.sgm.model.Tecnico;
import com.sgm.sgm.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sgm.sgm.exception.RecursoNaoEncontradoException;

import java.util.List;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    public List<Tecnico> listarTodos() {
        return tecnicoRepository.findAll();
    }

    public Tecnico buscarPorId(Long id) {
        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Técnico não encontrado"));
    }

    public Tecnico salvar(Tecnico tecnico) {
        return tecnicoRepository.save(tecnico);
    }

    public Tecnico atualizar(Long id, Tecnico dadosAtualizados) {
        Tecnico tecnico = buscarPorId(id);
        tecnico.setNome(dadosAtualizados.getNome());
        tecnico.setEspecialidade(dadosAtualizados.getEspecialidade());
        tecnico.setContato(dadosAtualizados.getContato());
        return tecnicoRepository.save(tecnico);
    }

    public void deletar(Long id) {
        Tecnico tecnico = buscarPorId(id);
        tecnicoRepository.delete(tecnico);
    }
}