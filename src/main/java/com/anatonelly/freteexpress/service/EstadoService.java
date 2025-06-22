package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Estado;
import com.anatonelly.freteexpress.model.Pais;
import com.anatonelly.freteexpress.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    @Autowired
    public EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    /**
     * Busca um Estado pelo nome. Se não encontrar, cria um novo associado ao País fornecido.
     * @param nome O nome do Estado a ser buscado/criado.
     * @param pais O objeto País ao qual o Estado pertence.
     * @return A entidade Estado encontrada ou recém-criada.
     */
    @Transactional
    public Estado findOrCreate(String nome, Pais pais) {
        // .orElseGet() é a forma elegante de "desembrulhar" o Optional:
        // se o estado for encontrado, ele é retornado; senão, a expressão lambda é executada.
        return estadoRepository.findByNome(nome)
                .orElseGet(() -> estadoRepository.save(new Estado(nome, pais)));
    }
}