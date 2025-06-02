package com.anatonelly.freteexpress.restcontroller;

import com.anatonelly.freteexpress.model.UsuarioAdministrador;
import com.anatonelly.freteexpress.service.UsuarioAdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administradores")
public class UsuarioAdministradorRestController {

    private final UsuarioAdministradorService service;

    public UsuarioAdministradorRestController(UsuarioAdministradorService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioAdministrador> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioAdministrador> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UsuarioAdministrador salvar(@RequestBody UsuarioAdministrador usuario) {
        return service.salvar(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioAdministrador> autenticar(@RequestParam String email,
                                                           @RequestParam String senha) {
        return service.autenticar(email, senha)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}
