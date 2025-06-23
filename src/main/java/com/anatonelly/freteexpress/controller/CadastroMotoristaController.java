package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.dto.CadastroMotoristaDTO;
import com.anatonelly.freteexpress.service.CadastroMotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroMotoristaController {

    @Autowired
    private CadastroMotoristaService cadastroMotoristaService;

    @GetMapping("/cadastro")
    public String showCadastro(Model model) {
        // Adiciona o DTO ao modelo para o data binding do formulário
        model.addAttribute("motoristaDTO", new CadastroMotoristaDTO());
        return "cadastroMotorista";
    }

    @PostMapping("/finalizar-cadastro")
    public String processCadastro(
            @ModelAttribute("motoristaDTO") CadastroMotoristaDTO motoristaDTO,
            RedirectAttributes redirectAttributes) {

        try {
            cadastroMotoristaService.cadastrarNovoMotorista(motoristaDTO);
        } catch (Exception e) {
            // Imprime o erro no console para depuração
            e.printStackTrace();
            // Adiciona uma mensagem de erro para o usuário
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao finalizar cadastro: " + e.getMessage());
            return "redirect:/cadastro";
        }

        // Redireciona para a página de login com mensagem de sucesso
        redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso! Faça seu login.");
        return "redirect:/login";
    }
}