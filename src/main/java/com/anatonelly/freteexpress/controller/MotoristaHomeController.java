package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MotoristaHomeController {

    private final MotoristaRepository motoristaRepository;

    public MotoristaHomeController(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @GetMapping({"/motorista/home", "/home"})
    public String showMotoristaHome(Model model) {
        List<Motorista> motoristas = List.of();

        try {
            // Recupera motoristas do banco
            motoristas = motoristaRepository.findAll();
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
        }

        model.addAttribute("motoristas", motoristas);
        return "homeMotorista";
    }
}