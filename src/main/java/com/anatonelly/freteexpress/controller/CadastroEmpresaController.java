package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.dto.CadastroEmpresaDTO;
import com.anatonelly.freteexpress.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cadastro/empresa")
public class CadastroEmpresaController {

    private final CadastroService cadastroService;

    @Autowired
    public CadastroEmpresaController(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    /**
     * Método para exibir a página do formulário de cadastro.
     * @return o nome da view HTML do formulário.
     */
    @GetMapping("/passo1")
    public String exibirFormularioCadastro() {
        // Retorna o nome do seu arquivo HTML que contém o formulário de cadastro
        return "cadastro-empresa"; // ou o nome que você deu ao seu arquivo
    }

    /**
     * Método para processar o envio do formulário de cadastro.
     * @param empresaDTO O objeto preenchido com os dados do formulário.
     * @param redirectAttributes Usado para enviar mensagens para a próxima página após o redirecionamento.
     * @return uma string de redirecionamento para a página de sucesso ou de erro.
     */
    @PostMapping("/passo1/submit") // Garanta que esta URL é a mesma do 'action' do seu formulário
    public String processarCadastroEmpresa(@ModelAttribute CadastroEmpresaDTO empresaDTO, RedirectAttributes redirectAttributes) {

        // Validações básicas podem ser feitas aqui se desejar, antes de chamar o serviço.
        if (empresaDTO.getNomeEmpresa() == null || empresaDTO.getNomeEmpresa().isEmpty() ||
            empresaDTO.getEmail() == null || empresaDTO.getEmail().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Nome da empresa e email são obrigatórios.");
            return "redirect:/cadastro/empresa/passo1";
        }

        try {
            // Delega TODA a lógica de negócio para o serviço com uma única chamada.
            cadastroService.cadastrarNovaEmpresa(empresaDTO);
        } catch (Exception e) {
            // Se qualquer erro acontecer na camada de serviço, capturamos aqui.
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao realizar o cadastro. Tente novamente.");
            // Logar o erro real no console do servidor é uma boa prática
            e.printStackTrace();
            return "redirect:/cadastro/empresa/passo1";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Empresa cadastrada com sucesso!");
        // Redireciona para a página de login ou uma página de sucesso.
        return "redirect:/login";
    }
}