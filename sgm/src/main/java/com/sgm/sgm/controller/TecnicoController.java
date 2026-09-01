package com.sgm.sgm.controller;

import com.sgm.sgm.model.Tecnico;
import com.sgm.sgm.service.TecnicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping
    public List<Tecnico> listarTodos() {
        return tecnicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tecnicoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tecnico criar(@Valid @RequestBody Tecnico tecnico) {
        return tecnicoService.salvar(tecnico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tecnico> atualizar(@PathVariable Long id, @Valid @RequestBody Tecnico tecnico) {
        return ResponseEntity.ok(tecnicoService.atualizar(id, tecnico));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        tecnicoService.deletar(id);
    }
}