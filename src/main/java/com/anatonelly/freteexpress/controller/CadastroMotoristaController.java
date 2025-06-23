package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.dto.CadastroMotoristaDTO; // Precisaremos de um DTO para o cadastro de motorista
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.model.Veiculo; // Se o veículo também for persistido aqui
import com.anatonelly.freteexpress.model.Endereco;
import com.anatonelly.freteexpress.model.Cidade;
import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.model.Pais;

import com.anatonelly.freteexpress.service.MotoristaService;
import com.anatonelly.freteexpress.service.EnderecoService;
import com.anatonelly.freteexpress.service.CidadeService;
import com.anatonelly.freteexpress.service.EstadoService;
import com.anatonelly.freteexpress.service.PaisService;
import com.anatonelly.freteexpress.service.VeiculoService; // Se houver um serviço de veículo

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // Usaremos ModelAttribute
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; // Apenas se houver campos avulsos, preferir ModelAttribute
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensagens de sucesso/erro

import org.hibernate.Hibernate; // Para inicialização de Lazy Loading
import java.util.Optional; // Para Optional returns de serviços

@Controller
public class CadastroMotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private EnderecoService enderecoService; // Para salvar o endereço do motorista

    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private EstadoService estadoService;
    @Autowired
    private PaisService paisService;
    @Autowired(required = false) // Pode não existir ainda, ajuste conforme seu projeto
    private VeiculoService veiculoService; // Se houver serviço para o veículo


    // Método GET para exibir o formulário de cadastro
    @GetMapping("/cadastro")
    public ModelAndView showCadastro(ModelAndView modelAndView) {
        modelAndView.setViewName("cadastroMotorista");
        modelAndView.addObject("cadastroMotoristaDTO", new CadastroMotoristaDTO()); // Passa um DTO vazio para o formulário
        return modelAndView;
    }

    // Método POST para processar o cadastro
    @PostMapping("/finalizar-cadastro")
    public String processCadastro(
            @ModelAttribute("cadastroMotoristaDTO") CadastroMotoristaDTO dto, // Recebe os dados via DTO
            @RequestParam("foto") MultipartFile foto, // MultipartFile ainda é @RequestParam
            RedirectAttributes redirectAttributes) { // Para mensagens de sucesso/erro

        // --- VALIDAÇÃO BÁSICA (Ajuste conforme seus campos obrigatórios no DTO) ---
        if (dto.getEmail() == null || dto.getEmail().isEmpty() ||
                dto.getSenha() == null || dto.getSenha().isEmpty() ||
                dto.getNomeCompleto() == null || dto.getNomeCompleto().isEmpty() ||
                dto.getCpf() == null || dto.getCpf().isEmpty() ||
                dto.getRua() == null || dto.getRua().isEmpty() ||
                dto.getCidadeNome() == null || dto.getCidadeNome().isEmpty() ||
                dto.getEstadoNome() == null || dto.getEstadoNome().isEmpty() ||
                dto.getCelular() == null || dto.getCelular().isEmpty()) {

            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, preencha todos os campos obrigatórios.");
            redirectAttributes.addFlashAttribute("cadastroMotoristaDTO", dto); // Repassa o DTO para preencher o form
            return "redirect:/cadastro"; // Redireciona de volta para o formulário
        }

        try {
            // --- LÓGICA DE PERSISTÊNCIA (Similar ao Cadastro de Empresa) ---
            // 1. Encontrar ou criar País
            Pais pais = paisService.findByNome("Brasil")
                    .orElseGet(() -> {
                        Pais novoPais = new Pais();
                        novoPais.setNome("Brasil");
                        return paisService.save(novoPais);
                    });

            // 2. Encontrar ou criar Estado (depende do País)
            Estado estado = estadoService.findByNome(dto.getEstadoNome())
                    .orElseGet(() -> {
                        Estado novoEstado = new Estado();
                        novoEstado.setNome(dto.getEstadoNome());
                        novoEstado.setSigla("XX"); // Assuma um padrão ou adicione ao DTO
                        novoEstado.setPais(pais);
                        return estadoService.save(novoEstado);
                    });

            // 3. Encontrar ou criar Cidade (depende do Estado)
            Cidade cidade = cidadeService.findByNome(dto.getCidadeNome())
                    .orElseGet(() -> {
                        Cidade novaCidade = new Cidade();
                        novaCidade.setNome(dto.getCidadeNome());
                        novaCidade.setEstado(estado);
                        return cidadeService.save(novaCidade);
                    });

            // 4. Criar e Salvar Endereço
            Endereco endereco = new Endereco();
            endereco.setRua(dto.getRua());
            endereco.setBairro(dto.getBairro());
            endereco.setCep(dto.getCep());
            endereco.setComplemento(dto.getComplemento()); // Pode ser null
            endereco.setCidade(cidade);

            try {
                if (dto.getNumero() != null && !dto.getNumero().trim().isEmpty()) {
                    endereco.setNumero(Integer.parseInt(dto.getNumero()));
                } else {
                    endereco.setNumero(null);
                }
            } catch (NumberFormatException e) {
                System.err.println("AVISO: Número de endereço não é válido: '" + dto.getNumero() + "'. Definindo como nulo.");
                endereco.setNumero(null);
            }
            Endereco savedEndereco = enderecoService.save(endereco); // Salva o endereço

            // 5. Criar e Salvar Motorista
            Motorista motorista = new Motorista();
            motorista.setEmail(dto.getEmail());
            motorista.setSenha(dto.getSenha()); // A senha deve ser criptografada no MotoristaService.salvar()
            motorista.setNomeCompleto(dto.getNomeCompleto());
            motorista.setCpf(dto.getCpf());
            motorista.setCelular(dto.getCelular());
            motorista.setCnh(dto.getCnh()); // Adicione ao DTO e formulário se for obrigatório
            motorista.setEndereco(savedEndereco); // Atribui o endereço salvo

            // Processa a foto (ex: salva no sistema de arquivos ou DB como BLOB)
            if (foto != null && !foto.isEmpty()) {
                motorista.setImagemPerfil(foto.getBytes()); // Converte para byte[] para BLOB
            }

            motorista = motoristaService.salvar(motorista); // Salva o motorista (a senha será criptografada aqui)

            // 6. Criar e Salvar Veículo (se aplicável e os campos estiverem no DTO)
            if (veiculoService != null) { // Apenas se VeiculoService estiver injetado
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(dto.getPlaca()); // Adicionar ao DTO e formulario
                veiculo.setModelo(dto.getModelo()); // Adicionar ao DTO e formulario
                veiculo.setAno(dto.getAno()); // Adicionar ao DTO e formulario
                veiculo.setTipo(dto.getTipo()); // Adicionar ao DTO e formulario
                veiculo.setMotorista(motorista); // Associa o veículo ao motorista salvo
                // ... outros campos do veículo do DTO ...
                veiculoService.salvar(veiculo); // Salva o veículo
            }

            redirectAttributes.addFlashAttribute("successMessage", "Cadastro de motorista realizado com sucesso!");
            return "redirect:/login"; // Redireciona para a página de login
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro no console para depuração
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao realizar o cadastro: " + e.getMessage());
            redirectAttributes.addFlashAttribute("cadastroMotoristaDTO", dto); // Repassa o DTO para preencher o form
            return "redirect:/cadastro"; // Redireciona de volta para o formulário
        }
    }

    // Método GET para exibir a página de login
    @GetMapping("/login")
    public ModelAndView showLogin() {
        return new ModelAndView("loginMotorista");
    }
}
