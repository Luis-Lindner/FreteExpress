package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.FreteService;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional; // Import para Optional

@Controller
@RequestMapping("/motorista/historico")
public class HistoricoFreteMotoristaController {

    @Autowired
    private FreteService freteMotoristaService;

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping
    public String showHistoricoFretes(Model model) {
        // Obtém o usuário autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() == "anonymousUser") {
            model.addAttribute("error", "Você precisa estar logado para visualizar o histórico.");
            return "historico-fretes";
        }

        // Recupera o motorista com base no username (email ou outro identificador)
        String username = auth.getName(); // Geralmente o email ou username

        // CORRIGIDO: Trata o Optional retornado por findByEmail()
        // Usa orElse(null) para que 'motorista' seja null se não encontrado,
        // o que corresponde à lógica de verificação subsequente.
        Motorista motorista = motoristaService.findByEmail(username).orElse(null);

        if (motorista == null) {
            model.addAttribute("error", "Motorista não encontrado para o usuário logado.");
            return "historico-fretes";
        }

        try {
            List<Frete> fretesFinalizados = freteMotoristaService.getFretesFinalizadosPorMotorista(motorista);
            model.addAttribute("fretes", fretesFinalizados);
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar o histórico de fretes: " + e.getMessage());
        }
        return "historico-fretes";
    }
}
