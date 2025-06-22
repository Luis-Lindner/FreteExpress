package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Empresa;
import com.anatonelly.freteexpress.model.Endereco;
import com.anatonelly.freteexpress.model.Cidade;
import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.model.Pais;

import com.anatonelly.freteexpress.service.EmpresaClienteService;
import com.anatonelly.freteexpress.service.EnderecoService;
import com.anatonelly.freteexpress.service.CidadeService;
import com.anatonelly.freteexpress.service.EstadoService;
import com.anatonelly.freteexpress.service.PaisService; // Import correto

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.hibernate.Hibernate; // Import para inicializar proxies

@Controller
@RequestMapping("/cadastro/empresa")
public class CadastroEmpresaController {

    @Autowired
    private EmpresaClienteService empresaClienteService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private PaisService paisService;

    @GetMapping("/passo1")
    public String showCadastroEmpresaPasso1(Model model) {
        Empresa empresa;
        Endereco endereco;

        if (model.containsAttribute("empresa")) {
            empresa = (Empresa) model.getAttribute("empresa");
            if (empresa.getEndereco() == null) {
                endereco = new Endereco();
                empresa.setEndereco(endereco);
            } else {
                endereco = empresa.getEndereco();
                // Inicialize as entidades carregadas lazy aqui também, se vierem do flash e forem usadas imediatamente
                if (endereco.getCidade() != null) {
                    Hibernate.initialize(endereco.getCidade());
                    if (endereco.getCidade().getEstado() != null) {
                        Hibernate.initialize(endereco.getCidade().getEstado());
                        if (endereco.getCidade().getEstado().getPais() != null) {
                            Hibernate.initialize(endereco.getCidade().getEstado().getPais());
                        }
                    }
                }
            }
        } else {
            empresa = new Empresa();
            endereco = new Endereco();
            empresa.setEndereco(endereco);
        }

        // Garante que objetos aninhados não sejam nulos para o binding do formulário
        if (endereco.getCidade() == null) {
            endereco.setCidade(new Cidade());
        }
        if (endereco.getCidade().getEstado() == null) {
            endereco.getCidade().setEstado(new Estado());
        }
        if (endereco.getCidade().getEstado().getPais() == null) {
            endereco.getCidade().getEstado().setPais(new Pais());
        }

        model.addAttribute("empresa", empresa);
        return "cadastro-empresa-passo1";
    }

    @PostMapping("/passo1")
    public String submitCadastroEmpresaPasso1(
            @ModelAttribute("empresa") Empresa empresa,
            RedirectAttributes redirectAttributes) {

        // --- INÍCIO: LINHAS DE DEBUG ---
        System.out.println("--- DEBUGGING FORM BINDING ---");
        System.out.println("Empresa:");
        System.out.println("  Email: " + empresa.getEmail());
        System.out.println("  Senha: " + empresa.getSenha());
        System.out.println("  Nome: " + empresa.getNome());
        System.out.println("  CNPJ: " + empresa.getCnpj());

        Endereco endereco = empresa.getEndereco();
        System.out.println("Endereco:");
        if (endereco != null) {
            System.out.println("  Rua: " + endereco.getRua());
            System.out.println("  Número: " + endereco.getNumero());
            System.out.println("  Bairro: " + endereco.getBairro());
            System.out.println("  CEP: " + endereco.getCep());

            if (endereco.getCidade() != null) {
                System.out.println("  Cidade (objeto): NÃO NULO");
                System.out.println("  Cidade Nome: " + endereco.getCidade().getNome());
                if (endereco.getCidade().getEstado() != null) {
                    System.out.println("  Cidade Estado (objeto): NÃO NULO");
                    System.out.println("  Cidade Estado Nome: " + endereco.getCidade().getEstado().getNome());
                    if (endereco.getCidade().getEstado().getPais() != null) {
                        System.out.println("  Cidade Estado País (objeto): NÃO NULO");
                        System.out.println("  Cidade Estado País Nome: " + endereco.getCidade().getEstado().getPais().getNome());
                    } else {
                        System.out.println("  Cidade Estado País (objeto): NULO");
                    }
                } else {
                    System.out.println("  Cidade Estado (objeto): NULO");
                }
            } else {
                System.out.println("  Cidade (objeto): NULO");
            }
        } else {
            System.out.println("  Endereco (objeto): NULO");
        }
        System.out.println("--- FIM DEBUGGING FORM BINDING ---");
        // --- FIM: LINHAS DE DEBUG ---

        // --- INÍCIO: Validação dos campos ---
        if (empresa.getEmail() == null || empresa.getEmail().isEmpty() ||
                empresa.getSenha() == null || empresa.getSenha().isEmpty() ||
                empresa.getNome() == null || empresa.getNome().isEmpty() ||
                empresa.getCnpj() == null || empresa.getCnpj().isEmpty() ||
                empresa.getEndereco() == null ||
                empresa.getEndereco().getRua() == null || empresa.getEndereco().getRua().isEmpty() ||
                empresa.getEndereco().getNumero() == null ||
                empresa.getEndereco().getBairro() == null || empresa.getEndereco().getBairro().isEmpty() ||
                empresa.getEndereco().getCep() == null || empresa.getEndereco().getCep().isEmpty() ||
                empresa.getEndereco().getCidade() == null ||
                empresa.getEndereco().getCidade().getNome() == null || empresa.getEndereco().getCidade().getNome().isEmpty() ||
                empresa.getEndereco().getCidade().getEstado() == null ||
                empresa.getEndereco().getCidade().getEstado().getNome() == null || empresa.getEndereco().getCidade().getEstado().getNome().isEmpty()) {

            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, preencha todos os campos do passo 1.");
            redirectAttributes.addFlashAttribute("empresa", empresa);
            return "redirect:/cadastro/empresa/passo1";
        }
        // --- FIM: Validação dos campos ---


        Cidade cidade = cidadeService.findByNome(empresa.getEndereco().getCidade().getNome());
        if (cidade == null) {
            cidade = new Cidade();
            cidade.setNome(empresa.getEndereco().getCidade().getNome());

            Estado estado = estadoService.findByNome(empresa.getEndereco().getCidade().getEstado().getNome());
            if (estado == null) {
                estado = new Estado();
                estado.setNome(empresa.getEndereco().getCidade().getEstado().getNome());
                estado.setSigla("XX");

                Pais pais = paisService.findByNome("Brasil");
                if (pais == null) {
                    pais = new Pais();
                    pais.setNome("Brasil");
                    pais = paisService.save(pais);
                }
                estado.setPais(pais);
                estado = estadoService.save(estado);
            }
            cidade.setEstado(estado);
            cidade = cidadeService.save(cidade);
        }
        empresa.getEndereco().setCidade(cidade);


        // Armazena os dados do passo 1 nos flash attributes para o próximo passo
        // Força a inicialização das entidades lazy antes de passá-las para o flash attribute
        if (empresa.getEndereco() != null) {
            Hibernate.initialize(empresa.getEndereco());
            if (empresa.getEndereco().getCidade() != null) {
                Hibernate.initialize(empresa.getEndereco().getCidade());
                if (empresa.getEndereco().getCidade().getEstado() != null) {
                    Hibernate.initialize(empresa.getEndereco().getCidade().getEstado());
                    if (empresa.getEndereco().getCidade().getEstado().getPais() != null) {
                        Hibernate.initialize(empresa.getEndereco().getCidade().getEstado().getPais());
                    }
                }
            }
        }
        redirectAttributes.addFlashAttribute("empresa", empresa);
        return "redirect:/cadastro/empresa/passo2";
    }

    @GetMapping("/passo2")
    public String showCadastroEmpresaPasso2(@ModelAttribute("empresa") Empresa empresa, Model model) {
        if (empresa.getEmail() == null) {
            return "redirect:/cadastro/empresa/passo1";
        }
        model.addAttribute("empresa", empresa);
        return "cadastro-empresa-passo2";
    }

    @PostMapping("/concluir")
    public String concluirCadastroEmpresa(@ModelAttribute("empresa") Empresa empresa,
                                          RedirectAttributes redirectAttributes) {
        if (empresa.getRazaoSocial() == null || empresa.getRazaoSocial().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, preencha o campo Empresa (Razão Social).");
            redirectAttributes.addFlashAttribute("empresa", empresa);
            return "redirect:/cadastro/empresa/passo2";
        }

        try {
            Endereco savedEndereco = enderecoService.save(empresa.getEndereco());
            empresa.setEndereco(savedEndereco);

            empresaClienteService.cadastrarEmpresa(empresa);
            redirectAttributes.addFlashAttribute("successMessage", "Empresa cadastrada com sucesso!");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("empresa", empresa);
            return "redirect:/cadastro/empresa/passo2";
        }
    }

    @GetMapping("/voltar-login")
    public String voltarParaLogin() {
        return "redirect:/login";
    }

    @PostMapping("/voltar-passo1")
    public String voltarParaPasso1(@ModelAttribute("empresa") Empresa empresa,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("empresa", empresa);
        return "redirect:/cadastro/empresa/passo1";
    }
}
