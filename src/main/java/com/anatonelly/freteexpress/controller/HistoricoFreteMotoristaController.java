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
import java.util.Optional; // Importe o Optional se necessário

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
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            model.addAttribute("error", "Você precisa estar logado para visualizar o histórico.");
            return "historico-fretes"; // Nome da sua view
        }

        // --- INÍCIO DA CORREÇÃO ---

        String username = auth.getName(); // Geralmente o email ou username

        // 1. Receba o resultado em uma variável Optional
        Optional<Motorista> motoristaOpt = motoristaService.findByEmail(username);

        // 2. Verifique se o Optional contém um valor
        if (motoristaOpt.isPresent()) {
            // 3. Se sim, pegue o objeto de dentro do Optional
            Motorista motorista = motoristaOpt.get();

            try {
                List<Frete> fretesFinalizados = freteMotoristaService.getFretesFinalizadosPorMotorista(motorista);
                model.addAttribute("fretes", fretesFinalizados);
            } catch (Exception e) {
                model.addAttribute("error", "Erro ao carregar o histórico de fretes: " + e.getMessage());
            }

        } else {
            // 4. Se não, o motorista não foi encontrado
            model.addAttribute("error", "Motorista não encontrado para o usuário logado.");
        }

        // --- FIM DA CORREÇÃO ---

        return "historico-fretes"; // Nome da sua view
    }
}