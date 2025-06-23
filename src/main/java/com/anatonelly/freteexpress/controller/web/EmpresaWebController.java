package com.anatonelly.freteexpress.controller.web;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/empresa")
public class EmpresaWebController {

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping("/home")
    public String homeEmpresa(Model model) {
        List<Motorista> motoristas = motoristaService.listar();

        // Mapa para associar motorista ID com a string das estrelas
        // A chave do mapa deve ser Integer (tipo de idMotorista)
        Map<Integer, String> estrelasMap = new HashMap<>(); // <<<<< CORRIGIDO AQUI: Long para Integer
        for (Motorista m : motoristas) {
            // <<<<< CORRIGIDO AQUI: m.getId() para m.getIdMotorista()
            // <<<<< CORRIGIDO AQUI: m.getAvaliacao() (que já estava correto, mas reforçado)
            estrelasMap.put(m.getIdMotorista(), gerarEstrelas(m.getAvaliacao()));
        }

        model.addAttribute("motoristas", motoristas);
        model.addAttribute("estrelasMap", estrelasMap);

        return "home"; // Retorna o template empresa/homeEmpresa.html
    }

    // Método que gera a string das estrelas
    private String gerarEstrelas(int avaliacao) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < avaliacao; i++) {
            sb.append("★");
        }
        for (int i = avaliacao; i < 5; i++) {
            sb.append("☆");
        }
        return sb.toString();
    }
}
