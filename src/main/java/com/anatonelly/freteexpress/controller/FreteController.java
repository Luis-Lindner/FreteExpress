package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    @Autowired
    private FreteService freteService;

    @GetMapping
    public ResponseEntity<List<Frete>> listarTodos() {
        return ResponseEntity.ok(freteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frete> buscarPorId(@PathVariable Long id) {
        return freteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Frete> salvar(@RequestBody Frete frete) {
        return ResponseEntity.ok(freteService.salvar(frete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frete> atualizar(@PathVariable Long id, @RequestBody Frete frete) {
        return freteService.atualizar(id, frete)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (freteService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


