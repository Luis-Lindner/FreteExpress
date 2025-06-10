package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado findByNome(String nome) {
        return estadoRepository.findByNome(nome);
    }
}