package com.anatonelly.freteexpress.controller;

import org.springframework.stereotype.Controller;  // Importar essa annotation
import org.springframework.ui.Model;
import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.repository.EmpresaClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller  // <-- ESSE CARA FALTA AQUI
public class CadastroEmpresaController {

    @Autowired
    private EmpresaClienteRepository empresaClienteRepository;

    @GetMapping("/cadastroEmpresa")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("empresaCliente", new EmpresaCliente());
        return "CadastroEmpresa";  // Certifique-se que CadastroEmpresa.html está em /templates
    }

    @PostMapping("/cadastroEmpresa")
    public String salvarEmpresa(@ModelAttribute EmpresaCliente empresaCliente) {
        empresaClienteRepository.save(empresaCliente);
        return "redirect:/login"; // Redireciona após o cadastro
    }
}
