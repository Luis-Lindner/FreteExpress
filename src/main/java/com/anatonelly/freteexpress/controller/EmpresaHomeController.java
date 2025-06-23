package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/empresa")
public class EmpresaHomeController {

    private final MotoristaService motoristaService;

    public EmpresaHomeController(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;
    }

    @GetMapping("/home")
    public String homeEmpresa(Model model) {
        List<Motorista> motoristas = motoristaService.listar();

        // Simula dados de pagamento e avaliação para cada motorista
        for (int i = 0; i < motoristas.size(); i++) {
            Motorista motorista = motoristas.get(i);
            motorista.setStatusPagamento((i % 2 == 0) ? "Pago" : "Pendente");
            motorista.setAvaliacao((i % 2 == 0) ? 5 : 4);
        }

        model.addAttribute("motoristas", motoristas);
        model.addAttribute("activePage", "home");

        // CORREÇÃO: O retorno agora corresponde exatamente ao nome do seu arquivo HTML.
        return "home-empresa";
    }
}