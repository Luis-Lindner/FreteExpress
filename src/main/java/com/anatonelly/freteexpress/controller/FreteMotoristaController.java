package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.FreteRepository;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import com.anatonelly.freteexpress.service.FreteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class FreteMotoristaController {

    private final FreteService freteService;
    private final MotoristaRepository motoristaRepository;

    public FreteMotoristaController(FreteService freteService, MotoristaRepository motoristaRepository) {
        this.freteService = freteService;
        this.motoristaRepository = motoristaRepository;
    }

    @GetMapping("/motorista/fretes")
    public String showFretes(Model model) {
        try {
            List<Frete> fretes = freteService.listarTodos();
            model.addAttribute("fretes", fretes);
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar fretes: " + e.getMessage());
        }
        return "fretesMotorista";
    }

    @PostMapping("/motorista/solicitar-frete")
    public String solicitarFrete(@RequestParam Integer freteId, Model model) { // <<<<< CORRIGIDO AQUI: Long para Integer
        try {
            // Simulação: Motorista logado com ID 1 (ajuste conforme autenticação)
            // Use 1, sem 'L' para que seja Integer
            Optional<Motorista> motoristaOpt = motoristaRepository.findById(1); // <<<<< CORRIGIDO AQUI: 1L para 1
            if (motoristaOpt.isPresent()) {
                if (freteService.solicitarFrete(freteId, 1)) { // <<<<< CORRIGIDO AQUI: 1L para 1
                    model.addAttribute("message", "Frete solicitado com sucesso!");
                } else {
                    model.addAttribute("error", "Frete não disponível para solicitação.");
                }
            } else {
                model.addAttribute("error", "Motorista não encontrado.");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao solicitar frete: " + e.getMessage());
        }
        return "redirect:/motorista/fretes";
    }
}
