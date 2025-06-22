package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Pais;
import com.anatonelly.freteexpress.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaisService {

    private final PaisRepository paisRepository;

    @Autowired
    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    /**
     * Busca um País pelo nome. Se não encontrar, cria um novo.
     * @param nome O nome do País a ser buscado/criado.
     * @return A entidade Pais encontrada ou recém-criada.
     */
    @Transactional
    public Pais findOrCreate(String nome) {
        return paisRepository.findByNome(nome)
                .orElseGet(() -> paisRepository.save(new Pais(nome)));
    }
}