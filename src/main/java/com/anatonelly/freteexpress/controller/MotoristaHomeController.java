package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
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
        List<Motorista> motoristas = new ArrayList<>();

        try {
            // Recupera motoristas do banco
            motoristas = motoristaRepository.findAll();

            if (motoristas.isEmpty()) {
                // Removido o código de simulação de motoristas (m1, m2, m3)
            } else {
                // Simulação de status e avaliação para motoristas existentes
                for (int i = 0; i < motoristas.size(); i++) {
                    Motorista motorista = motoristas.get(i);
                    if (motorista.getId() == null) {
                        motorista.setId((long) (i + 1)); // Atribui IDs sequenciais se não houver
                    }
                    motorista.setStatusPagamento(i % 2 == 0 ? "Pago" : "Pendente");
                    motorista.setAvaliacao(i % 2 == 0 ? 5 : 0);
                }
            }

            // Criar mapa de estrelas de avaliação
            Map<Long, String> estrelasAvaliacao = new HashMap<>();
            for (Motorista motorista : motoristas) {
                if (motorista.getId() != null) {
                    estrelasAvaliacao.put(motorista.getId(), gerarEstrelas(motorista.getAvaliacao()));
                }
            }

            model.addAttribute("gestores", motoristas); // Passa a lista de 'motoristas' como 'gestores' para o HTML
            model.addAttribute("estrelasAvaliacao", estrelasAvaliacao); // Adicione o mapa ao modelo

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
        }

        model.addAttribute("motoristas", motoristas); // Mantido por compatibilidade
        return "homeMotorista";
    }

    // Método auxiliar para gerar a string das estrelas
    private String gerarEstrelas(int avaliacao) {
        if (avaliacao < 0 || avaliacao > 5) {
            avaliacao = 0; // Valor padrão se inválido
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