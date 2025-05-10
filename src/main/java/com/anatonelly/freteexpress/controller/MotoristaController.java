package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping
    public ResponseEntity<List<Motorista>> listarTodos() {
        return ResponseEntity.ok(motoristaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorista> buscarPorId(@PathVariable Long id) {
        return motoristaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Motorista> salvar(@RequestBody Motorista motorista) {
        return ResponseEntity.ok(motoristaService.salvar(motorista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorista> atualizar(@PathVariable Long id, @RequestBody Motorista motorista) {
        return motoristaService.atualizar(id, motorista)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (motoristaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

