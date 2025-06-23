package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.enums.StatusFrete;
import com.anatonelly.freteexpress.service.EmpresaClienteService;
import com.anatonelly.freteexpress.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/empresa/fretes")
@SessionAttributes("frete")
public class CadastroFreteEmpresaController {

    private final FreteService freteService;
    private final EmpresaClienteService empresaClienteService;

    @Autowired
    public CadastroFreteEmpresaController(FreteService freteService, EmpresaClienteService empresaClienteService) {
        this.freteService = freteService;
        this.empresaClienteService = empresaClienteService;
    }

    @ModelAttribute("frete")
    public Frete getFrete() {
        return new Frete();
    }

    @GetMapping("/novo/passo1")
    public String exibirPasso1(@ModelAttribute("frete") Frete frete) {
        return "cadastro-frete-p1";
    }

    @PostMapping("/novo/passo1")
    public String processarPasso1(@ModelAttribute("frete") Frete frete) {
        return "redirect:/empresa/fretes/novo/passo2";
    }

    @GetMapping("/novo/passo2")
    public String exibirPasso2(@ModelAttribute("frete") Frete frete) {
        return "cadastro-frete-p2";
    }

    @PostMapping("/novo/passo2")
    public String processarPasso2(@ModelAttribute("frete") Frete frete) {
        return "redirect:/empresa/fretes/novo/passo3";
    }

    @GetMapping("/novo/passo3")
    public String exibirPasso3(@ModelAttribute("frete") Frete frete) {
        return "cadastro-frete-p3";
    }

    @PostMapping("/novo/passo3")
    public String processarPasso3EFinalizar(@ModelAttribute("frete") Frete frete, SessionStatus status, Authentication authentication) {
        String emailEmpresa = authentication.getName();
        EmpresaCliente empresaLogada = empresaClienteService.findByEmail(emailEmpresa);

        frete.setEmpresaCliente(empresaLogada);
        frete.setStatus(StatusFrete.PENDENTE);

        freteService.salvar(frete);
        status.setComplete(); // limpa o frete da sessão

        return "redirect:/empresa/dashboard?sucesso=true";
    }
}
