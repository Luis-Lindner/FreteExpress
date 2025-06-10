package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Pais;
import com.anatonelly.freteexpress.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    public Pais save(Pais pais) {
        return paisRepository.save(pais);
    }

    public Pais findByNome(String nome) {
        return paisRepository.findByNome(nome);
    }
}