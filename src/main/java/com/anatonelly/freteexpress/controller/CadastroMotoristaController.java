package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroMotoristaController {

    @Autowired
    private MotoristaService motoristaService;

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
            @RequestParam String tipo,
            RedirectAttributes redirectAttributes) {

        try {
            // Cria o objeto Motorista com os dados do formulário
            Motorista motorista = new Motorista();
            motorista.setEmail(email);
            motorista.setSenha(senha);
            motorista.setNome(nome);
            motorista.setCpf(cpf);
            // Note: Os campos endereco, cidade, estado, celular, foto, placa, modelo, ano, tipo
            // não estão no modelo Motorista atual. Você pode precisar adicionar esses campos
            // ou criar entidades relacionadas (Endereco, Veiculo, etc.)

            // Salva o motorista no banco de dados
            motoristaService.salvar(motorista);

            // Adiciona mensagem de sucesso
            redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso!");

            System.out.println("Motorista cadastrado com sucesso:");
            System.out.println("Email: " + email);
            System.out.println("Nome: " + nome);
            System.out.println("CPF: " + cpf);

        } catch (Exception e) {
            // Em caso de erro, adiciona mensagem de erro
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao realizar cadastro: " + e.getMessage());
            System.err.println("Erro ao cadastrar motorista: " + e.getMessage());
            e.printStackTrace();
            return new ModelAndView("redirect:/cadastro");
        }

        // Redireciona para a página de login após o cadastro
        return new ModelAndView("redirect:/login");
    }
}