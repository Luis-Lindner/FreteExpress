package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/empresa")
public class EmpresaHomeController {

    private final MotoristaService motoristaService;

    public EmpresaHomeController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @GetMapping("/home")
    public String homeEmpresa(Model model) {
        List<Motorista> motoristas = motoristaService.listar();

        // --- INÍCIO DA CORREÇÃO ---

        // 1. Cria mapas para guardar os dados de exibição
        Map<Long, String> statusPagamentoMap = new HashMap<>();
        Map<Long, String> estrelasAvaliacaoMap = new HashMap<>();

        // 2. Simula dados de pagamento e avaliação para cada motorista
        for (int i = 0; i < motoristas.size(); i++) {
            Motorista motorista = motoristas.get(i);
            Long motoristaId = motorista.getId(); // Pega o ID para usar como chave no mapa

            // Gera os dados temporários
            String status = (i % 2 == 0) ? "Pago" : "Pendente";
            int avaliacao = (i % 2 == 0) ? 5 : 4;

            // Coloca os dados nos mapas em vez de tentar alterar o objeto motorista
            statusPagamentoMap.put(motoristaId, status);
            estrelasAvaliacaoMap.put(motoristaId, gerarEstrelas(avaliacao));
        }

        // 3. Adiciona a lista e os novos mapas ao modelo
        model.addAttribute("motoristas", motoristas);
        model.addAttribute("statusPagamentoMap", statusPagamentoMap);
        model.addAttribute("estrelasAvaliacao", estrelasAvaliacaoMap);
        model.addAttribute("activePage", "home");

        // --- FIM DA CORREÇÃO ---

        return "home-empresa";
    }

    /**
     * Método auxiliar privado para gerar estrelas a partir de um número.
     */
    private String gerarEstrelas(int avaliacao) {
        if (avaliacao < 0 || avaliacao > 5) {
            avaliacao = 0;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < avaliacao; i++) {
            sb.append("★"); // Estrela preenchida
        }
        for (int i = avaliacao; i < 5; i++) {
            sb.append("☆"); // Estrela vazia
        }
        return sb.toString();
    }
}