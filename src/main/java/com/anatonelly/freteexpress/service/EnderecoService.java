package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Endereco;
import com.anatonelly.freteexpress.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
}