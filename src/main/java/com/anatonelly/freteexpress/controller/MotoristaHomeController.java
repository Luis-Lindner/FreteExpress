package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.model.Endereco;
import com.anatonelly.freteexpress.model.Cidade;
import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.model.Pais;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication; // Importe Authentication
import org.springframework.security.core.context.SecurityContextHolder; // Importe SecurityContextHolder

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class MotoristaHomeController {

    private final MotoristaRepository motoristaRepository;

    public MotoristaHomeController(MotoristaRepository motoristaRepository) {
        this.motoristaRepository = motoristaRepository;
    }

    @GetMapping({"/motorista/home", "/home"})
    @Transactional // Garante que a sessão do Hibernate esteja aberta
    public String showMotoristaHome(Model model) {
        // Obtém o motorista logado diretamente do contexto de segurança
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            // Isso não deveria acontecer se as regras de segurança estiverem corretas, mas é um fallback
            return "redirect:/login";
        }

        Motorista motoristaLogado = (Motorista) auth.getPrincipal();

        // Inicialize o relacionamento 'endereco' e seus aninhados para o motorista logado
        if (motoristaLogado.getEndereco() != null) {
            Hibernate.initialize(motoristaLogado.getEndereco());
            if (motoristaLogado.getEndereco().getCidade() != null) {
                Hibernate.initialize(motoristaLogado.getEndereco().getCidade());
                if (motoristaLogado.getEndereco().getCidade().getEstado() != null) {
                    Hibernate.initialize(motoristaLogado.getEndereco().getCidade().getEstado());
                    if (motoristaLogado.getEndereco().getCidade().getEstado().getPais() != null) {
                        Hibernate.initialize(motoristaLogado.getEndereco().getCidade().getEstado().getPais());
                    }
                }
            }
        }

        model.addAttribute("motoristaLogado", motoristaLogado); // Adiciona o motorista logado ao modelo


        List<Motorista> motoristas = new ArrayList<>();
        try {
            motoristas = motoristaRepository.findAll();

            for (int i = 0; i < motoristas.size(); i++) {
                Motorista motorista = motoristas.get(i);
                // Inicialize o relacionamento 'endereco' e seus aninhados para cada motorista na lista
                if (motorista.getEndereco() != null) {
                    Hibernate.initialize(motorista.getEndereco());
                    if (motorista.getEndereco().getCidade() != null) {
                        Hibernate.initialize(motorista.getEndereco().getCidade());
                        if (motorista.getEndereco().getCidade().getEstado() != null) {
                            Hibernate.initialize(motorista.getEndereco().getCidade().getEstado());
                            if (motorista.getEndereco().getCidade().getEstado().getPais() != null) {
                                Hibernate.initialize(motorista.getEndereco().getCidade().getEstado().getPais());
                            }
                        }
                    }
                }

                if (motorista.getIdMotorista() == null) {
                    motorista.setIdMotorista(i + 1);
                }
                motorista.setStatusPagamento(i % 2 == 0 ? "Pago" : "Pendente");
                motorista.setAvaliacao(i % 2 == 0 ? 5 : 0);
            }

            Map<Integer, String> estrelasAvaliacao = new HashMap<>();
            for (Motorista motorista : motoristas) {
                if (motorista.getIdMotorista() != null) {
                    estrelasAvaliacao.put(motorista.getIdMotorista(), gerarEstrelas(motorista.getAvaliacao()));
                }
            }

            model.addAttribute("gestores", motoristas);
            model.addAttribute("estrelasAvaliacao", estrelasAvaliacao);

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }

        return "homeMotorista";
    }

    private String gerarEstrelas(int avaliacao) {
        if (avaliacao < 0 || avaliacao > 5) {
            avaliacao = 0;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < avaliacao; i++) {
            sb.append("★");
        }
        for (int i = avaliacao; i < 5; i++) {
            sb.append("☆");
        }
        return sb.toString();
    }
}
