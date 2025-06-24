package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.FreteService;
import com.anatonelly.freteexpress.service.MotoristaService; // Importe o MotoristaService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Importe a classe Authentication
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class FreteMotoristaController {

    private final FreteService freteService;
    private final MotoristaService motoristaService; // Use o Service em vez do Repository

    @Autowired
    public FreteMotoristaController(FreteService freteService, MotoristaService motoristaService) {
        this.freteService = freteService;
        this.motoristaService = motoristaService;
    }

    @GetMapping("/motorista/fretes")
    public String showFretes(Model model) {
        try {
            List<Frete> fretes = freteService.listarTodos();
            model.addAttribute("fretes", fretes);
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar fretes: " + e.getMessage());
        }
        return "freteMotorista"; // Corrigido para "freteMotorista" que é o nome do seu template
    }

    @PostMapping("/motorista/solicitar-frete")
    public String solicitarFrete(@RequestParam Long freteId, RedirectAttributes redirectAttributes, Authentication authentication) {
        // 1. Verifica se o usuário está autenticado
        if (authentication == null || !authentication.isAuthenticated()) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para solicitar um frete.");
            return "redirect:/login";
        }

        try {
            // 2. Pega o email do usuário logado
            String email = authentication.getName();
            Optional<Motorista> motoristaOpt = motoristaService.findByEmail(email);

            if (motoristaOpt.isPresent()) {
                Motorista motoristaLogado = motoristaOpt.get();
                // 3. Chama o serviço com o ID dinâmico do motorista logado
                if (freteService.solicitarFrete(freteId, motoristaLogado.getId())) {
                    redirectAttributes.addFlashAttribute("message", "Frete solicitado com sucesso!");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Frete não disponível para solicitação.");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Motorista não encontrado.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao solicitar frete: " + e.getMessage());
        }
        return "redirect:/motorista/fretes";
    }
}