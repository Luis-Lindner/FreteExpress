package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.AvaliacaoMotorista;
import com.anatonelly.freteexpress.service.AvaliacaoMotoristaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes-motorista")
public class AvaliacaoMotoristaController {

    private final AvaliacaoMotoristaService service;

    public AvaliacaoMotoristaController(AvaliacaoMotoristaService service) {
        this.service = service;
    }

    @GetMapping
    public List<AvaliacaoMotorista> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoMotorista> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AvaliacaoMotorista salvar(@RequestBody AvaliacaoMotorista avaliacaoMotorista) {
        return service.salvar(avaliacaoMotorista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
