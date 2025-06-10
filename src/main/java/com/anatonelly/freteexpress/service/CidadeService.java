package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Cidade;
import com.anatonelly.freteexpress.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade save(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade findByNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }
}