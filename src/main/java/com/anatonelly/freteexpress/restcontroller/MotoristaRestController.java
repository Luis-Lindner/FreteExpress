package com.anatonelly.freteexpress.restcontroller;

import com.anatonelly.freteexpress.model.Motorista;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/motorista")
public class MotoristaRestController {

    private List<Motorista> motoristas = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Motorista> saveMotorista(@RequestBody Motorista motorista) {
        motoristas.add(motorista);
        return ResponseEntity.ok(motorista);
    }

    @GetMapping
    public ResponseEntity<List<Motorista>> getAllMotoristas() {
        return ResponseEntity.ok(motoristas);
    }
}