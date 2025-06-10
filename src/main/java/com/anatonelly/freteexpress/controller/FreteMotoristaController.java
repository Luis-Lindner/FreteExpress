package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FreteMotoristaController {

    @Autowired
    private FreteRepository freteRepository;

    @GetMapping("/motorista/fretes")
    public String showFretesMotorista(Model model) {
        try {
            List<Frete> fretes = freteRepository.findAll();

            List<Frete> pendentes = new ArrayList<>();
            List<Frete> aceitos = new ArrayList<>();
            List<Frete> finalizados = new ArrayList<>();

            LocalDateTime agora = LocalDateTime.now();

            for (Frete frete : fretes) {
                if (frete.getStatus() != null && "Pendente".equalsIgnoreCase(frete.getStatus())) {
                    pendentes.add(frete);
                } else if (frete.getStatus() != null && "Aceito".equalsIgnoreCase(frete.getStatus())) {
                    aceitos.add(frete);
                } else if (frete.getStatus() != null && "Finalizado".equalsIgnoreCase(frete.getStatus())) {
                    finalizados.add(frete);
                } else {
                    if (frete.getPrazoEntrega() != null && frete.getPrazoEntrega().isAfter(agora)) {
                        pendentes.add(frete);
                    } else if (frete.getPrazoEntrega() != null && frete.getPrazoEntrega().isBefore(agora)) {
                        finalizados.add(frete);
                    }
                }
            }

            model.addAttribute("pendentes", pendentes);
            model.addAttribute("aceitos", aceitos);
            model.addAttribute("finalizados", finalizados);
            model.addAttribute("pendentesCount", pendentes.size());
            model.addAttribute("aceitosCount", aceitos.size());
            model.addAttribute("finalizadosCount", finalizados.size());

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar fretes: " + e.getMessage());
        }
        return "freteMotorista";
    }
}