package com.anatonelly.freteexpress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CadastroMotoristaController {

    @GetMapping("/cadastro")
    public ModelAndView showCadastro() {
        return new ModelAndView("cadastroMotorista");
    }

    @PostMapping("/finalizar-cadastro")
    public ModelAndView processCadastro(
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String nome,
            @RequestParam String cpf,
            @RequestParam String endereco,
            @RequestParam String cidade,
            @RequestParam String estado,
            @RequestParam String celular,
            @RequestParam("foto") MultipartFile foto,
            @RequestParam String placa,
            @RequestParam String modelo,
            @RequestParam int ano,
            @RequestParam String tipo) {
        // Processa todos os dados do cadastro
        System.out.println("Email: " + email);
        System.out.println("Senha: " + senha);
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Endereço: " + endereco);
        System.out.println("Cidade: " + cidade);
        System.out.println("Estado: " + estado);
        System.out.println("Celular: " + celular);
        System.out.println("Foto: " + (foto != null ? foto.getOriginalFilename() : "Nenhuma foto"));
        System.out.println("Placa: " + placa);
        System.out.println("Modelo: " + modelo);
        System.out.println("Ano: " + ano);
        System.out.println("Tipo de Veículo: " + tipo);

        // Redireciona para a página de login
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/login")
    public ModelAndView showLogin() {
        return new ModelAndView("loginMotorista");
    }
}