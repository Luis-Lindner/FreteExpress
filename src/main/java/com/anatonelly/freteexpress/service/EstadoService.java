package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional; // Import para Optional

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    // CORRIGIDO: O tipo de retorno agora é Optional<Estado>
    public Optional<Estado> findByNome(String nome) {
        return estadoRepository.findByNome(nome);
    }

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }
}
