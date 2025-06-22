package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Cidade;
import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    @Autowired
    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    // Este método é mais útil: ele busca uma cidade pelo nome. Se não existir, ele a cria.
    @Transactional // Garante a consistência da operação
    public Cidade findOrCreate(String nome, Estado estado) {
        return cidadeRepository.findByNome(nome)
                .orElseGet(() -> cidadeRepository.save(new Cidade(nome, estado)));
    }
}