package com.anatonelly.freteexpress.controller;

import com.anatonelly.freteexpress.model.Frete;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.enums.StatusFrete;
import com.anatonelly.freteexpress.service.FreteService;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/motorista")
public class FreteMotoristaController {

    @Autowired
    private FreteService freteService;

    @Autowired
    private MotoristaService motoristaService;

    /**
     * Exibe a página de fretes disponíveis para motoristas
     */
    @GetMapping("/fretes")
    public String showFretes(
            @RequestParam(value = "pesquisa", required = false) String pesquisa,
            Model model) {
        try {
            List<Frete> fretes;

            if (pesquisa != null && !pesquisa.trim().isEmpty()) {
                // Se houver pesquisa, filtra por tipo de carga
                fretes = freteService.listarTodos().stream()
                        .filter(f -> f.getTipoCarga() != null &&
                                f.getTipoCarga().toLowerCase().contains(pesquisa.toLowerCase()))
                        .toList();
                model.addAttribute("pesquisa", pesquisa);
            } else {
                // Caso contrário, lista todos os fretes disponíveis
                fretes = freteService.listarTodos();
            }

            // Calcula estatísticas para os boxes
            long contagemPendentes = fretes.stream()
                    .filter(f -> f.getStatus() == StatusFrete.PENDENTE)
                    .count();
            long contagemAceitos = fretes.stream()
                    .filter(f -> f.getStatus() == StatusFrete.ACEITO)
                    .count();
            long contagemFinalizados = fretes.stream()
                    .filter(f -> f.getStatus() == StatusFrete.FINALIZADO)
                    .count();

            model.addAttribute("fretes", fretes);
            model.addAttribute("contagemPendentes", contagemPendentes);
            model.addAttribute("contagemAceitos", contagemAceitos);
            model.addAttribute("contagemFinalizados", contagemFinalizados);
            model.addAttribute("activePage", "fretes");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar fretes: " + e.getMessage());
            model.addAttribute("fretes", List.of());
            model.addAttribute("contagemPendentes", 0);
            model.addAttribute("contagemAceitos", 0);
            model.addAttribute("contagemFinalizados", 0);
            e.printStackTrace();
        }

        return "fretesMotorista";
    }

    /**
     * Exibe os detalhes de um frete específico
     */
    @GetMapping("/fretes/{id}")
    public String showFreteDetalhes(@PathVariable Long id, Model model) {
        try {
            Optional<Frete> freteOpt = freteService.buscarPorId(id);

            if (freteOpt.isPresent()) {
                model.addAttribute("frete", freteOpt.get());
            } else {
                model.addAttribute("frete", null);
                model.addAttribute("error", "Frete não encontrado.");
            }

            model.addAttribute("activePage", "fretes");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar detalhes do frete: " + e.getMessage());
            model.addAttribute("frete", null);
            e.printStackTrace();
        }

        return "detalhesFrete";
    }

    /**
     * Processa a solicitação de um frete por um motorista
     */
    @PostMapping("/fretes/{id}/solicitar")
    public String solicitarFrete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            // Simulação: Motorista logado com ID 1 (Integer)
            // Em um sistema real, você pegaria do SecurityContext
            Integer motoristaId = 1;

            // Verifica se o motorista existe
            Optional<Motorista> motoristaOpt = motoristaService.findByEmail("motorista@teste.com");

            if (motoristaOpt.isEmpty()) {
                // Se não existe, cria um motorista de teste
                Motorista motoristaTeste = new Motorista();
                motoristaTeste.setNome("Motorista Teste");
                motoristaTeste.setEmail("motorista@teste.com");
                motoristaTeste.setSenha("123456");
                motoristaTeste.setCpf("12345678901");
                motoristaService.salvar(motoristaTeste);
                System.out.println("Motorista de teste criado para demonstração");
            }

            // Tenta solicitar o frete
            boolean sucesso = freteService.solicitarFrete(id, motoristaId);

            if (sucesso) {
                redirectAttributes.addFlashAttribute("successMessage", "Frete solicitado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Frete não disponível para solicitação.");
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao solicitar frete: " + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/motorista/fretes";
    }

    /**
     * Lista os fretes do motorista (aceitos/em andamento)
     */
    @GetMapping("/meus-fretes")
    public String showMeusFretes(Model model) {
        try {
            // Simulação: busca fretes do motorista ID 1
            // Em um sistema real, você pegaria do usuário logado
            List<Frete> meusFretes = freteService.listarTodos().stream()
                    .filter(f -> f.getMotoristaSolicitante() != null &&
                            f.getMotoristaSolicitante().getId() == 1L)
                    .toList();

            model.addAttribute("fretes", meusFretes);
            model.addAttribute("activePage", "meus-fretes");

        } catch (Exception e) {
            model.addAttribute("error", "Erro ao carregar seus fretes: " + e.getMessage());
            model.addAttribute("fretes", List.of());
            e.printStackTrace();
        }

        return "meusFretes";
    }
}