package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Pais;
import com.anatonelly.freteexpress.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional; // Import para Optional

@Service
public class PaisService {

    @Autowired
    private PaisRepository paisRepository;

    // CORRIGIDO: O tipo de retorno agora é Optional<Pais>
    public Optional<Pais> findByNome(String nome) {
        return paisRepository.findByNome(nome);
    }

    public Pais save(Pais pais) {
        return paisRepository.save(pais);
    }
}
