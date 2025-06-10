package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class MotoristaHomeController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    @GetMapping({"/motorista/home", "/home"})
    public String showMotoristaHome(Model model) {
        try {
            List<Motorista> motoristas = motoristaRepository.findAll();
            model.addAttribute("motoristas", motoristas);

            // Simulação de status de pagamento e avaliação para cada motorista
            for (Motorista motorista : motoristas) {
                motorista.setStatusPagamento(motoristas.indexOf(motorista) % 2 == 0 ? "Pago" : "Pendente");
                motorista.setAvaliacao(motoristas.indexOf(motorista) % 2 == 0 ? 5 : 0);
            }

            // Se não houver motoristas, adicionar dados simulados
            if (motoristas.isEmpty()) {
                Motorista m1 = new Motorista();
                m1.setNomeCompleto("Fulano Ciciano Beltrano da Silva");
                m1.setStatusPagamento("Pago");
                m1.setAvaliacao(5);

                Motorista m2 = new Motorista();
                m2.setNomeCompleto("Fulano Ciciano Beltrano da Silva");
                m2.setStatusPagamento("Pago");
                m2.setAvaliacao(5);

                Motorista m3 = new Motorista();
                m3.setNomeCompleto("Fulano Ciciano Beltrano da Silva");
                m3.setStatusPagamento("Pendente");
                m3.setAvaliacao(0);

                motoristas = new java.util.ArrayList<>(Arrays.asList(m1, m2, m3));
                model.addAttribute("motoristas", motoristas);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
        }
        return "homeMotorista";
    }
}