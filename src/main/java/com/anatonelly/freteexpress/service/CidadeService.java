package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Cidade;
import com.anatonelly.freteexpress.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional; // Import para Optional

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    // CORRIGIDO: O tipo de retorno agora é Optional<Cidade>
    public Optional<Cidade> findByNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }

    public Cidade save(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }
}
