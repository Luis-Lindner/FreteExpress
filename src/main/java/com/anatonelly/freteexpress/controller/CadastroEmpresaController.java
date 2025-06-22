package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.EmpresaCliente; // Renomeado o import
import com.anatonelly.freteexpress.model.Endereco;
import com.anatonelly.freteexpress.model.Cidade;
import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.model.Pais;

import com.anatonelly.freteexpress.service.EmpresaClienteService;
import com.anatonelly.freteexpress.service.EnderecoService;
import com.anatonelly.freteexpress.service.CidadeService;
import com.anatonelly.freteexpress.service.EstadoService;
import com.anatonelly.freteexpress.service.PaisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.hibernate.Hibernate; // Importe Hibernate para inicializar proxies

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
        EmpresaCliente empresaCliente;
        Endereco endereco;

        if (model.containsAttribute("empresaCliente")) {
            empresaCliente = (EmpresaCliente) model.getAttribute("empresaCliente");
            if (empresaCliente.getEndereco() == null) {
                endereco = new Endereco();
                empresaCliente.setEndereco(endereco);
            } else {
                endereco = empresaCliente.getEndereco();
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
            empresaCliente = new EmpresaCliente();
            endereco = new Endereco();
            empresaCliente.setEndereco(endereco);
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

        model.addAttribute("empresaCliente", empresaCliente);
        return "cadastro-empresa-passo1";
    }

    @PostMapping("/passo1")
    public String submitCadastroEmpresaPasso1(
            @ModelAttribute("empresaCliente") EmpresaCliente empresaCliente,
            RedirectAttributes redirectAttributes) {

        System.out.println("--- DEBUGGING FORM BINDING ---");
        System.out.println("EmpresaCliente:");
        System.out.println("  Email: " + empresaCliente.getEmail());
        System.out.println("  Senha: " + empresaCliente.getSenha());
        System.out.println("  Nome: " + empresaCliente.getNome());
        System.out.println("  CNPJ: " + empresaCliente.getCnpj());

        Endereco endereco = empresaCliente.getEndereco();
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

        if (empresaCliente.getEmail() == null || empresaCliente.getEmail().isEmpty() ||
                empresaCliente.getSenha() == null || empresaCliente.getSenha().isEmpty() ||
                empresaCliente.getNome() == null || empresaCliente.getNome().isEmpty() ||
                empresaCliente.getCnpj() == null || empresaCliente.getCnpj().isEmpty() ||
                empresaCliente.getEndereco() == null ||
                empresaCliente.getEndereco().getRua() == null || empresaCliente.getEndereco().getRua().isEmpty() ||
                empresaCliente.getEndereco().getNumero() == null ||
                empresaCliente.getEndereco().getBairro() == null || empresaCliente.getEndereco().getBairro().isEmpty() ||
                empresaCliente.getEndereco().getCep() == null || empresaCliente.getEndereco().getCep().isEmpty() ||
                empresaCliente.getEndereco().getCidade() == null ||
                empresaCliente.getEndereco().getCidade().getNome() == null || empresaCliente.getEndereco().getCidade().getNome().isEmpty() ||
                empresaCliente.getEndereco().getCidade().getEstado() == null ||
                empresaCliente.getEndereco().getCidade().getEstado().getNome() == null || empresaCliente.getEndereco().getCidade().getEstado().getNome().isEmpty()) {

            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, preencha todos os campos do passo 1.");
            redirectAttributes.addFlashAttribute("empresaCliente", empresaCliente);
            return "redirect:/cadastro/empresa/passo1";
        }

        // --- INÍCIO DA LÓGICA CORRIGIDA para buscar/criar e salvar País, Estado e Cidade ---
        // 1. Encontrar ou criar País
        Pais pais = paisService.findByNome("Brasil")
                .orElseGet(() -> {
                    Pais novoPais = new Pais();
                    novoPais.setNome("Brasil");
                    return paisService.save(novoPais); // Salva o novo país e retorna
                });

        // 2. Encontrar ou criar Estado (depende do País)
        Estado estado = estadoService.findByNome(empresaCliente.getEndereco().getCidade().getEstado().getNome())
                .orElseGet(() -> {
                    Estado novoEstado = new Estado();
                    novoEstado.setNome(empresaCliente.getEndereco().getCidade().getEstado().getNome());
                    // Assumimos que a sigla será "XX" se o estado for novo. Você pode querer um campo no formulário para isso.
                    novoEstado.setSigla("XX");
                    novoEstado.setPais(pais); // Associa o país encontrado/criado
                    return estadoService.save(novoEstado); // Salva o novo estado e retorna
                });

        // 3. Encontrar ou criar Cidade (depende do Estado)
        Cidade cidade = cidadeService.findByNome(empresaCliente.getEndereco().getCidade().getNome())
                .orElseGet(() -> {
                    Cidade novaCidade = new Cidade();
                    novaCidade.setNome(empresaCliente.getEndereco().getCidade().getNome());
                    novaCidade.setEstado(estado); // Associa o estado encontrado/criado
                    return cidadeService.save(novaCidade); // Salva a nova cidade e retorna
                });
        // --- FIM DA LÓGICA CORRIGIDA ---

        empresaCliente.getEndereco().setCidade(cidade); // Define a cidade (existente ou recém-criada) no endereço da empresa

        // Força a inicialização das entidades lazy antes de passá-las para o flash attribute
        if (empresaCliente.getEndereco() != null) {
            Hibernate.initialize(empresaCliente.getEndereco());
            if (empresaCliente.getEndereco().getCidade() != null) {
                Hibernate.initialize(empresaCliente.getEndereco().getCidade());
                if (empresaCliente.getEndereco().getCidade().getEstado() != null) {
                    Hibernate.initialize(empresaCliente.getEndereco().getCidade().getEstado());
                    if (empresaCliente.getEndereco().getCidade().getEstado().getPais() != null) {
                        Hibernate.initialize(empresaCliente.getEndereco().getCidade().getEstado().getPais());
                    }
                }
            }
        }
        redirectAttributes.addFlashAttribute("empresaCliente", empresaCliente);
        return "redirect:/cadastro/empresa/passo2";
    }

    @GetMapping("/passo2")
    public String showCadastroEmpresaPasso2(@ModelAttribute("empresaCliente") EmpresaCliente empresaCliente, Model model) {
        if (empresaCliente.getEmail() == null) {
            return "redirect:/cadastro/empresa/passo1";
        }
        model.addAttribute("empresaCliente", empresaCliente);
        return "cadastro-empresa-passo2";
    }

    @PostMapping("/concluir")
    public String concluirCadastroEmpresa(@ModelAttribute("empresaCliente") EmpresaCliente empresaCliente,
                                          RedirectAttributes redirectAttributes) {
        if (empresaCliente.getRazaoSocial() == null || empresaCliente.getRazaoSocial().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, preencha o campo Empresa (Razão Social).");
            redirectAttributes.addFlashAttribute("empresaCliente", empresaCliente);
            return "redirect:/cadastro/empresa/passo2";
        }

        try {
            Endereco savedEndereco = enderecoService.save(empresaCliente.getEndereco());
            empresaCliente.setEndereco(savedEndereco);

            empresaClienteService.cadastrarEmpresa(empresaCliente);
            redirectAttributes.addFlashAttribute("successMessage", "Empresa cadastrada com sucesso!");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("empresaCliente", empresaCliente);
            return "redirect:/cadastro/empresa/passo2";
        }
    }

    @GetMapping("/voltar-login")
    public String voltarParaLogin() {
        return "redirect:/login";
    }

    @PostMapping("/voltar-passo1")
    public String voltarParaPasso1(@ModelAttribute("empresaCliente") EmpresaCliente empresaCliente,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("empresaCliente", empresaCliente);
        return "redirect:/cadastro/empresa/passo1";
    }
}
