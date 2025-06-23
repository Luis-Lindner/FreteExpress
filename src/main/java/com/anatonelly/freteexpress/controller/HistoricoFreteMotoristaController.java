package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.FreteService;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/motorista/historico")
public class HistoricoFreteMotoristaController {

    // 1. Injeção de dependências via construtor (melhor prática)
    private final FreteService freteService;
    private final MotoristaService motoristaService;

    public HistoricoFreteMotoristaController(FreteService freteService, MotoristaService motoristaService) {
        this.freteService = freteService;
        this.motoristaService = motoristaService;
    }

    @GetMapping
    public String showHistoricoFretes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 2. Verificação de autenticação corrigida e com redirecionamento
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/login"; // Redireciona para a página de login
        }

        String username = auth.getName();
        Motorista motorista = motoristaService.findByEmail(username);

        if (motorista == null) {
            model.addAttribute("error", "Motorista não encontrado para o usuário logado.");
            model.addAttribute("fretes", Collections.emptyList());
            return "motorista/historico-fretes"; // 3. Caminho do template organizado
        }

        List<Frete> fretesFinalizados = freteService.getFretesFinalizadosPorMotorista(motorista);
        model.addAttribute("fretes", fretesFinalizados);

        return "motorista/historico-fretes"; // 3. Caminho do template organizado
    }
}