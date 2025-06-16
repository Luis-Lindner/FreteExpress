package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.EmpresaCliente;
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
        if (!model.containsAttribute("empresa")) {
            model.addAttribute("empresa", new EmpresaCliente());
            model.addAttribute("endereco", new Endereco());
        } else {
            EmpresaCliente empresa = (EmpresaCliente) model.getAttribute("empresa");
            // Certifique-se que está chamando getEndereco() e não getEndereço()
            if (empresa.getEndereco() == null) {
                model.addAttribute("endereco", new Endereco());
            } else {
                model.addAttribute("endereco", empresa.getEndereco());
            }
        }
        return "cadastro-empresa-passo1";
    }

    @PostMapping("/passo1")
    public String submitCadastroEmpresaPasso1(
            @ModelAttribute("empresa") EmpresaCliente empresa,
            @ModelAttribute("endereco") Endereco endereco,
            RedirectAttributes redirectAttributes) {

        // Validação (ajustada para campos de Endereco)
        if (empresa.getEmail() == null || empresa.getEmail().isEmpty() || // << getEmail()
                empresa.getSenha() == null || empresa.getSenha().isEmpty() || // << getSenha()
                empresa.getNome() == null || empresa.getNome().isEmpty() || // << getNome()
                empresa.getCnpj() == null || empresa.getCnpj().isEmpty() ||
                endereco.getRua() == null || endereco.getRua().isEmpty() ||
                endereco.getCidade() == null ||
                endereco.getCidade().getNome() == null || endereco.getCidade().getNome().isEmpty() || // Validação do nome da cidade
                endereco.getCidade().getEstado() == null ||
                endereco.getCidade().getEstado().getNome() == null || endereco.getCidade().getEstado().getNome().isEmpty()) { // Validação do nome do estado
            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, preencha todos os campos do passo 1.");
            redirectAttributes.addFlashAttribute("empresa", empresa);
            redirectAttributes.addFlashAttribute("endereco", endereco);
            return "redirect:/cadastro/empresa/passo1";
        }

        // Antes de redirecionar para o passo 2, precisamos mockar ou buscar as entidades de Cidade e Estado
        // Para o exemplo, vamos assumir que você tem um mock ou busca por nome
        // Em um cenário real, você buscaria Cidade/Estado pelo nome ou ID do formulário
        Cidade cidade = cidadeService.findByNome(endereco.getCidade().getNome());
        if (cidade == null) {
            cidade = new Cidade();
            cidade.setNome(endereco.getCidade().getNome());
            Estado estado = estadoService.findByNome(endereco.getCidade().getEstado().getNome());
            if (estado == null) {
                estado = new Estado();
                estado.setNome(endereco.getCidade().getEstado().getNome());
                estado.setSigla("XX"); // Exemplo de sigla, você pode precisar coletar isso do form ou ter um padrão
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
        endereco.setCidade(cidade);

        // Armazena os dados do passo 1 na sessão temporariamente ou redireciona com atributos
        empresa.setEndereco(endereco);

        redirectAttributes.addFlashAttribute("empresa", empresa);
        return "redirect:/cadastro/empresa/passo2";
    }

    @GetMapping("/passo2")
    public String showCadastroEmpresaPasso2(@ModelAttribute("empresa") EmpresaCliente empresa, Model model) {
        if (empresa.getEmail() == null) {
            return "redirect:/cadastro/empresa/passo1";
        }
        model.addAttribute("empresa", empresa);
        return "cadastro-empresa-passo2";
    }

    @PostMapping("/concluir")
    public String concluirCadastroEmpresa(@ModelAttribute("empresa") EmpresaCliente empresa,
                                          RedirectAttributes redirectAttributes) {
        if (empresa.getRazaoSocial() == null || empresa.getRazaoSocial().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Por favor, preencha o campo Empresa (Razão Social).");
            redirectAttributes.addFlashAttribute("empresa", empresa);
            return "redirect:/cadastro/empresa/passo2";
        }

        try {
            // Salvar o endereço primeiro, se ele não tiver um ID (ou seja, se for novo)
            // Se o endereço já tiver um ID (vindo de um retorno), ele será atualizado
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
    public String voltarParaPasso1(@ModelAttribute("empresa") EmpresaCliente empresa,
                                   RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("empresa", empresa);
        return "redirect:/cadastro/empresa/passo1";
    }
}