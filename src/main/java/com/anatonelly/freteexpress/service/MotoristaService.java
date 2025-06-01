package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Motorista;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MotoristaService {

    private List<Motorista> motoristas = new ArrayList<>();

    public void salvar(Motorista motorista) {
        motoristas.add(motorista);
    }

    public List<Motorista> listar() {
        return motoristas;
    }

    public boolean autenticar(String cpf, String senha) {
        return motoristas.stream()
                .anyMatch(m -> m.getCpf().equals(cpf) && m.getSenha().equals(senha));
    }
}
