package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MotoristaHomeController {

    private final MotoristaRepository motoristaRepository;

    public MotoristaHomeController(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @GetMapping({"/motorista/home", "/home"})
    public String showMotoristaHome(Model model) {
        try {
            // Para o motorista, mostramos uma lista de "empresas" (que são outros motoristas no sistema)
            // Isso simula empresas/gestores que podem contratar o motorista
            List<Motorista> motoristas = motoristaRepository.findAll();

            // Mapas para guardar os dados de exibição que não estão no modelo Motorista
            Map<Long, String> statusPagamentoMap = new HashMap<>();
            Map<Long, String> estrelasAvaliacaoMap = new HashMap<>();

            // Itera sobre os motoristas para gerar os dados de exibição
            for (int i = 0; i < motoristas.size(); i++) {
                Motorista motorista = motoristas.get(i);
                Long motoristaId = motorista.getId();

                // Lógica para gerar status e avaliação (simulação)
                String status = (i % 2 == 0) ? "Pago" : "Pendente";
                int avaliacao = (i % 2 == 0) ? 5 : 4; // Exemplo de avaliação

                // Armazena os dados gerados nos mapas, usando o ID do motorista como chave
                statusPagamentoMap.put(motoristaId, status);
                estrelasAvaliacaoMap.put(motoristaId, gerarEstrelas(avaliacao));
            }

            // Adiciona a lista de motoristas e os mapas ao modelo para o HTML usar
            model.addAttribute("motoristas", motoristas);
            model.addAttribute("statusPagamentoMap", statusPagamentoMap);
            model.addAttribute("estrelasAvaliacao", estrelasAvaliacaoMap);
            model.addAttribute("activePage", "home");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
            model.addAttribute("motoristas", List.of()); // Lista vazia em caso de erro
            model.addAttribute("statusPagamentoMap", new HashMap<>());
            model.addAttribute("estrelasAvaliacao", new HashMap<>());
            e.printStackTrace(); // Imprime o erro no console para depuração
        }

        return "homeMotorista";
    }

    /**
     * Método auxiliar para gerar a representação em estrelas de uma avaliação numérica.
     * @param avaliacao um número de 0 a 5.
     * @return uma string com estrelas preenchidas e vazias.
     */
    private String gerarEstrelas(int avaliacao) {
        if (avaliacao < 0 || avaliacao > 5) {
            avaliacao = 0; // Garante que a avaliação esteja no intervalo [0, 5]
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