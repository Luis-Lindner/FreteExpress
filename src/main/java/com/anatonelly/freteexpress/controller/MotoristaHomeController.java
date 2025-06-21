package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap; // Importar HashMap
import java.util.List;
import java.util.Map;   // Importar Map

@Controller
public class MotoristaHomeController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @GetMapping({"/motorista/home", "/home"})
    public String showMotoristaHome(Model model) {
        try {
            List<Motorista> motoristas = motoristaRepository.findAll();

            // --- INÍCIO DAS ALTERAÇÕES ---

            // Se não houver motoristas, adicionar dados simulados (SUA LÓGICA EXISTENTE)
            if (motoristas.isEmpty()) {
                Motorista m1 = new Motorista();
                m1.setNomeCompleto("Fulano Ciciano Beltrano da Silva");
                m1.setStatusPagamento("Pago");
                m1.setAvaliacao(5);
                m1.setId(1L); // Importante: adicione IDs para os motoristas simulados
                // Isso é crucial para que o mapa de estrelas funcione corretamente.

                Motorista m2 = new Motorista();
                m2.setNomeCompleto("Fulano Ciciano Beltrano da Silva");
                m2.setStatusPagamento("Pago");
                m2.setAvaliacao(5);
                m2.setId(2L);

                Motorista m3 = new Motorista();
                m3.setNomeCompleto("Fulano Ciciano Beltrano da Silva");
                m3.setStatusPagamento("Pendente");
                m3.setAvaliacao(0);
                m3.setId(3L);

                motoristas.addAll(Arrays.asList(m1, m2, m3));
            } else {
                // Simulação de status de pagamento e avaliação para cada motorista existente
                // CERTIFIQUE-SE DE QUE SEUS OBJETOS Motorista TÊM UM ID VÁLIDO.
                // Se eles vierem do banco de dados, o ID já virá.
                for (Motorista motorista : motoristas) {
                    motorista.setStatusPagamento(motoristas.indexOf(motorista) % 2 == 0 ? "Pago" : "Pendente");
                    motorista.setAvaliacao(motoristas.indexOf(motorista) % 2 == 0 ? 5 : 0);
                }
            }

            model.addAttribute("gestores", motoristas); // Passa a lista de 'motoristas' como 'gestores' para o HTML

            // Crie um mapa para associar o ID do motorista (gestor) com a string das estrelas
            Map<Long, String> estrelasAvaliacao = new HashMap<>();
            for (Motorista gestor : motoristas) {
                // Adicione uma verificação para motoristas sem ID (ex: os simulados sem ID)
                if (gestor.getId() != null) {
                    estrelasAvaliacao.put(gestor.getId(), gerarEstrelas(gestor.getAvaliacao()));
                }
            }

            model.addAttribute("estrelasAvaliacao", estrelasAvaliacao); // Adicione o mapa ao modelo

            // --- FIM DAS ALTERAÇÕES ---

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
        }
        return "homeMotorista";
    }

    // Método auxiliar para gerar a string das estrelas
    private String gerarEstrelas(int avaliacao) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < avaliacao; i++) {
            sb.append("★"); // Estrela preenchida
        }
        for (int i = avaliacao; i < 5; i++) { // Garante 5 estrelas no total (preenchidas + vazias)
            sb.append("☆"); // Estrela vazia
        }
        return sb.toString();
    }
}