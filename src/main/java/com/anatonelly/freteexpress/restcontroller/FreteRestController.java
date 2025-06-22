package com.anatonelly.freteexpress.restcontroller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fretes")
public class FreteRestController {

    @Autowired
    private FreteService freteService;

    @GetMapping
    public ResponseEntity<List<Frete>> listarTodos() {
        return ResponseEntity.ok(freteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Frete> buscarPorId(@PathVariable Integer id) { // <<<<< CORRIGIDO AQUI: Long para Integer
        return freteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Frete> salvar(@RequestBody Frete frete) {
        return ResponseEntity.ok(freteService.salvar(frete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Frete> atualizar(@PathVariable Integer id, @RequestBody Frete frete) { // <<<<< CORRIGIDO AQUI: Long para Integer
        return freteService.atualizar(id, frete)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) { // <<<<< CORRIGIDO AQUI: Long para Integer
        if (freteService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
