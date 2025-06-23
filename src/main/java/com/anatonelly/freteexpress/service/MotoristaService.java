package com.anatonelly.freteexpress.service;

import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional; // Import para Optional

@Service
// Ao implementar UserDetailsService, esta classe se torna a ponte entre seus usuários e o Spring Security.
public class MotoristaService implements UserDetailsService {

    // Dependência do repositório para acessar os dados dos motoristas no banco.
    private final MotoristaRepository motoristaRepository;

    // Dependência do codificador de senhas para segurança.
    private final PasswordEncoder passwordEncoder;

    /**
     * Construtor para injeção de dependências.
     * O Spring automaticamente fornecerá instâncias de MotoristaRepository e PasswordEncoder.
     * @param motoristaRepository O repositório para operações de banco de dados com Motorista.
     * @param passwordEncoder O bean para codificar e verificar senhas.
     */
    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository, PasswordEncoder passwordEncoder) {
        this.motoristaRepository = motoristaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Salva um novo motorista, garantindo que a senha seja criptografada antes de persistir.
     * @param motorista O objeto Motorista com os dados a serem salvos.
     * @return O objeto Motorista salvo com a senha já criptografada.
     */
    public Motorista salvar(Motorista motorista) {
        String senhaCriptografada = passwordEncoder.encode(motorista.getSenha());
        motorista.setSenha(senhaCriptografada);
        return motoristaRepository.save(motorista);
    }

    /**
     * Retorna uma lista com todos os motoristas cadastrados no banco de dados.
     * @return Uma lista de objetos Motorista.
     */
    public List<Motorista> listar() {
        return motoristaRepository.findAll();
    }

    /**
     * Busca um motorista específico pelo seu endereço de email.
     * Usado por outros serviços ou controllers que precisam encontrar um motorista.
     * @param email O email a ser buscado.
     * @return O objeto Motorista se encontrado, encapsulado em um Optional.
     */
    public Optional<Motorista> findByEmail(String email) { // Retorna Optional para melhor tratamento de nulls
        return motoristaRepository.findByEmail(email);
    }

    /**
     * Método principal da interface UserDetailsService.
     * O Spring Security chama este método durante o processo de autenticação.
     * @param username O nome de usuário (que no nosso caso é o email) fornecido na tela de login.
     * @return Um objeto UserDetails que o Spring Security usa para validar a senha.
     * @throws UsernameNotFoundException se nenhum usuário for encontrado com o email fornecido.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Usa o método findByEmail que retorna Optional
        Motorista motorista = motoristaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));

        // Retorna um objeto User do Spring Security. Ele pega o email (username),
        // a senha criptografada do banco, e uma lista de permissões (roles/authorities).
        // Por enquanto, a lista de permissões está vazia.
        return new User(motorista.getEmail(), motorista.getSenha(), new ArrayList<>());
    }
}
