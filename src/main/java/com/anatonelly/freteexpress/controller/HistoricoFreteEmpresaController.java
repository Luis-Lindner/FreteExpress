package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.service.EmpresaClienteService;
import com.anatonelly.freteexpress.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/empresa/historico")
public class HistoricoFreteEmpresaController {

    private final FreteService freteService;
    private final EmpresaClienteService empresaClienteService;

    @Autowired
    public HistoricoFreteEmpresaController(FreteService freteService, EmpresaClienteService empresaClienteService) {
        this.freteService = freteService;
        this.empresaClienteService = empresaClienteService;
    }

    @GetMapping
    public String exibirHistorico(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/login";
        }
        String emailEmpresa = auth.getName();
        EmpresaCliente empresaLogada = empresaClienteService.findByEmail(emailEmpresa);

        if (empresaLogada == null) {
            model.addAttribute("error", "Empresa não encontrada para o usuário logado.");
            model.addAttribute("fretes", Collections.emptyList());
            return "empresa/historico-fretes"; // Caminho do template organizado
        }

        List<Frete> historicoFretes = freteService.findByEmpresa(empresaLogada);
        model.addAttribute("fretes", historicoFretes);
        model.addAttribute("nomeEmpresa", empresaLogada.getNome());

        return "empresa/historico-fretes"; // Caminho do template organizado
    }
}