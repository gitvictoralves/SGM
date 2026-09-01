package com.sgm.sgm.controller;

import com.sgm.sgm.dto.NovaOrdemRequest;
import com.sgm.sgm.model.HistoricoStatus;
import com.sgm.sgm.model.OrdemManutencao;
import com.sgm.sgm.model.StatusOrdem;
import com.sgm.sgm.service.OrdemManutencaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordens")
public class OrdemManutencaoController {

    @Autowired
    private OrdemManutencaoService ordemService;

    @GetMapping
    public List<OrdemManutencao> listarTodos() {
        return ordemService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemManutencao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordemService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrdemManutencao abrir(@Valid @RequestBody NovaOrdemRequest request) {
        return ordemService.abrir(request);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrdemManutencao> atualizarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        StatusOrdem novoStatus = StatusOrdem.valueOf(body.get("status"));
        return ResponseEntity.ok(ordemService.atualizarStatus(id, novoStatus));
    }

    @GetMapping("/{id}/historico")
    public List<HistoricoStatus> listarHistorico(@PathVariable Long id) {
        return ordemService.listarHistorico(id);
    }
}