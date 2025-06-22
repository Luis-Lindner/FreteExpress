package com.anatonelly.freteexpress.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // ESTA LINHA PERMITE ACESSO A QUALQUER URL SEM LOGIN
                .requestMatchers("/**").permitAll() 
            )
            // Desativar a proteção CSRF pode ser útil para testar formulários com Postman,
            // mas lembre-se de reativar para produção.
            .csrf(csrf -> csrf.disable()); 
            
        return http.build();
    }
}