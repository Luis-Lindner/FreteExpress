package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.service.EmpresaClienteService;
import com.anatonelly.freteexpress.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaDashboardController {

    private final FreteService freteService;
    private final EmpresaClienteService empresaClienteService;

    @Autowired
    public EmpresaDashboardController(FreteService freteService, EmpresaClienteService empresaClienteService) {
        this.freteService = freteService;
        this.empresaClienteService = empresaClienteService;
    }

    @GetMapping("/dashboard")
    public String exibirDashboard(Model model, Authentication authentication) {
        System.out.println("Authentication: " + authentication);
        System.out.println("Is Authenticated: " + (authentication != null && authentication.isAuthenticated()));
        if (authentication == null || !authentication.isAuthenticated()) {
            System.out.println("Redirecionando para /login devido a falha de autenticação.");
            return "redirect:/login";
        }

        String emailEmpresa = authentication.getName();
        System.out.println("Email da empresa logada: " + emailEmpresa);
        EmpresaCliente empresaLogada = empresaClienteService.findByEmail(emailEmpresa);

        if (empresaLogada != null) {
            List<Frete> meusFretes = freteService.findByEmpresa(empresaLogada);
            model.addAttribute("fretes", meusFretes);
        } else {
            model.addAttribute("fretes", Collections.emptyList());
            model.addAttribute("error", "Não foi possível carregar os dados da empresa.");
        }

        model.addAttribute("activePage", "dashboard");
        return "dashboard-fretes";
    }
}