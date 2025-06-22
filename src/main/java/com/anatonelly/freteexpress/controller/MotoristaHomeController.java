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
                // Se a lista estiver vazia, pode ser útil adicionar alguns dados de teste aqui
                // ou garantir que o banco tenha motoristas para testar.
            } else {
                // Simulação de status e avaliação para motoristas existentes
                for (int i = 0; i < motoristas.size(); i++) {
                    Motorista motorista = motoristas.get(i);
                    // Verificação de ID: Se o ID é nulo, atribui um ID sequencial.
                    // Ajustado para setIdMotorista e para Integer.
                    if (motorista.getIdMotorista() == null) { // Usar getIdMotorista()
                        motorista.setIdMotorista(i + 1); // Atribui IDs sequenciais se não houver (Integer)
                    }
                    motorista.setStatusPagamento(i % 2 == 0 ? "Pago" : "Pendente");
                    motorista.setAvaliacao(i % 2 == 0 ? 5 : 0);
                }
            }

            // Criar mapa de estrelas de avaliação
            Map<Integer, String> estrelasAvaliacao = new HashMap<>(); // Chave do mapa para Integer (ID do motorista)
            for (Motorista motorista : motoristas) {
                if (motorista.getIdMotorista() != null) { // Usar getIdMotorista()
                    estrelasAvaliacao.put(motorista.getIdMotorista(), gerarEstrelas(motorista.getAvaliacao())); // Usar getIdMotorista() e getAvaliacao()
                }
            }

            model.addAttribute("gestores", motoristas); // Passa a lista de 'motoristas' como 'gestores' para o HTML
            model.addAttribute("estrelasAvaliacao", estrelasAvaliacao); // Adicione o mapa ao modelo

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace para depuração
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
