package com.anatonelly.freteexpress.restcontroller;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.service.EmpresaClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaClienteRestController {

    private final EmpresaClienteService service;

    public EmpresaClienteRestController(EmpresaClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmpresaCliente> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaCliente> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmpresaCliente salvar(@RequestBody EmpresaCliente empresaCliente) {
        return service.salvar(empresaCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
