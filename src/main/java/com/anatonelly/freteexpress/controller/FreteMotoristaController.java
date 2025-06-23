package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.enums.StatusFrete;
import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.repository.FreteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motorista")
public class FreteMotoristaController {

    private final FreteRepository freteRepository;

    public FreteMotoristaController(FreteRepository freteRepository) {
        this.freteRepository = freteRepository;
    }

    @GetMapping("/fretes")
    public String listarFretesDisponiveis(@RequestParam(value = "pesquisa", required = false) String pesquisa, Model model) {
        // ... (lógica de busca continua a mesma)
        List<Frete> fretesDisponiveis;
        if (pesquisa != null && !pesquisa.trim().isEmpty()) {
            fretesDisponiveis = freteRepository.findByStatusAndTipoCargaContainingIgnoreCase(StatusFrete.PENDENTE, pesquisa);
        } else {
            fretesDisponiveis = freteRepository.findByStatus(StatusFrete.PENDENTE);
        }

        // ... (lógica de contagem continua a mesma)
        model.addAttribute("fretes", fretesDisponiveis);
        // ...

        // --- ALTERAÇÃO AQUI ---
        // Informando ao HTML que a página ativa é a de "fretes"
        model.addAttribute("activePage", "fretes");

        return "freteMotorista";
    }

    @GetMapping("/fretes/{id}")
    public String mostrarDetalhesFrete(@PathVariable("id") Long id, Model model) {
        Optional<Frete> freteOptional = freteRepository.findById(id);
        if (freteOptional.isPresent()) {
            model.addAttribute("frete", freteOptional.get());

            // --- ALTERAÇÃO AQUI ---
            // A página de detalhes também deve marcar o menu "fretes" como ativo
            model.addAttribute("activePage", "fretes");

            return "detalhesFrete";
        } else {
            return "redirect:/motorista/fretes?error=nao_encontrado";
        }
    }

    // O PostConstruct não precisa de alterações
    @PostConstruct
    public void criarDadosDeExemplo() {
        // ...
    }
}