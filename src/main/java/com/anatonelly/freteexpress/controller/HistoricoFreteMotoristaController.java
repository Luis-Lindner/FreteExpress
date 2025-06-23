package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.model.EmpresaCliente; // Para inicializar relacionamentos em Frete
import com.anatonelly.freteexpress.model.Endereco;     // Para inicializar relacionamentos em Frete
import com.anatonelly.freteexpress.model.Cidade;       // Para inicializar relacionamentos em Frete
import com.anatonelly.freteexpress.model.Estado;       // Para inicializar relacionamentos em Frete
import com.anatonelly.freteexpress.model.TipoVeiculo; // Para inicializar relacionamentos em Frete
import com.anatonelly.freteexpress.model.Carroceria; // Para inicializar relacionamentos em Frete


import com.anatonelly.freteexpress.service.FreteService;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate; // Importe Hibernate para inicializar proxies
import org.springframework.transaction.annotation.Transactional; // Para garantir que a sessão esteja aberta

@Controller
@RequestMapping("/motorista/historico")
public class HistoricoFreteMotoristaController {

    @Autowired
    private FreteService freteService; // Renomeado de freteMotoristaService para clareza

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping
    @Transactional // Garante que a sessão do Hibernate esteja aberta
    public String showHistoricoFretes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/login";
        }

        String username = auth.getName();
        Motorista motorista = motoristaService.findByEmail(username).orElse(null);

        if (motorista == null) {
            model.addAttribute("error", "Motorista não encontrado para o usuário logado.");
            model.addAttribute("fretes", Collections.emptyList());
            return "historico-fretes";
        }

        try {
            List<Frete> fretesFinalizados = freteService.getFretesFinalizadosPorMotorista(motorista);

            // --- INÍCIO: Inicialização Específica para Fretes e seus relacionamentos ---
            for (Frete frete : fretesFinalizados) {
                // Inicialize o motorista solicitante (se houver)
                if (frete.getMotoristaSolicitante() != null) {
                    Hibernate.initialize(frete.getMotoristaSolicitante());
                }
                // Inicialize a empresa (se houver)
                if (frete.getEmpresaCliente() != null) {
                    Hibernate.initialize(frete.getEmpresaCliente());
                }
                // Inicialize endereços de origem e destino e seus aninhados
                if (frete.getOrigem() != null) { // Assumindo getOrigem retorna Endereco
                    Hibernate.initialize(frete.getOrigem());
                    if (frete.getOrigem().getCidade() != null) {
                        Hibernate.initialize(frete.getOrigem().getCidade());
                        if (frete.getOrigem().getCidade().getEstado() != null) {
                            Hibernate.initialize(frete.getOrigem().getCidade().getEstado());
                            if (frete.getOrigem().getCidade().getEstado().getPais() != null) {
                                Hibernate.initialize(frete.getOrigem().getCidade().getEstado().getPais());
                            }
                        }
                    }
                }
                if (frete.getDestino() != null) { // Assumindo getDestino retorna Endereco
                    Hibernate.initialize(frete.getDestino());
                    if (frete.getDestino().getCidade() != null) {
                        Hibernate.initialize(frete.getDestino().getCidade());
                        if (frete.getDestino().getCidade().getEstado() != null) {
                            Hibernate.initialize(frete.getDestino().getCidade().getEstado());
                            if (frete.getDestino().getCidade().getEstado().getPais() != null) {
                                Hibernate.initialize(frete.getDestino().getCidade().getEstado().getPais());
                            }
                        }
                    }
                }
                // Se Frete tiver relacionamentos com TipoVeiculo, Carroceria, etc., inicialize-os aqui também
                if (frete.getTipoVeiculo() != null) {
                    Hibernate.initialize(frete.getTipoVeiculo());
                }
                if (frete.getCarroceria() != null) {
                    Hibernate.initialize(frete.getCarroceria());
                }
            }
            // --- FIM: Inicialização Específica ---

            model.addAttribute("fretes", fretesFinalizados);
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar o histórico de fretes: " + e.getMessage());
            e.printStackTrace(); // Para ver o stack trace no console
        }
        return "historico-fretes";
    }
}
