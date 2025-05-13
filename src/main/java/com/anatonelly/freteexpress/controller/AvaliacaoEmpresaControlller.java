package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.AvaliacaoEmpresa;
import com.anatonelly.freteexpress.service.AvaliacaoEmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes-empresa")

public class AvaliacaoEmpresaControlller {
    private AvaliacaoEmpresaService service;

    public void AvaliacaoEmpresaController(AvaliacaoEmpresaService service) {
        this.service = service;
    }

    public AvaliacaoEmpresaControlller(AvaliacaoEmpresaService service) {
        this.service = service;
    }

    @GetMapping
    public List<AvaliacaoEmpresa> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoEmpresa> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AvaliacaoEmpresa salvar(@RequestBody AvaliacaoEmpresa avaliacaoEmpresa) {
        return service.salvar(avaliacaoEmpresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
