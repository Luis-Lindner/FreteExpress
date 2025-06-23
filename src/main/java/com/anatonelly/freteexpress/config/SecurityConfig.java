package com.anatonelly.freteexpress.config;

import com.anatonelly.freteexpress.model.EmpresaCliente;
import com.anatonelly.freteexpress.model.Motorista;
import com.anatonelly.freteexpress.service.EmpresaClienteService;
import com.anatonelly.freteexpress.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate; // Não é mais estritamente necessário aqui se toString() é sobrescrito

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private EmpresaClienteService empresaClienteService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o PasswordEncoder que vem do AppConfig

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/login", "/cadastro", "/cadastro/empresa/passo1", "/cadastro/empresa/passo2", "/cadastro/empresa/concluir", "/finalizar-cadastro").permitAll()
                        .requestMatchers("/motorista/**").hasRole("MOTORISTA") // URLs para Motorista
                        .requestMatchers("/empresa/**").hasRole("EMPRESA")     // URLs para Empresa
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String email = authentication.getName();
                String senhaDigitada = authentication.getCredentials().toString();

                // 1. Tentar autenticar como Motorista
                Optional<Motorista> motoristaOpt = motoristaService.findByEmail(email);
                if (motoristaOpt.isPresent()) {
                    Motorista motorista = motoristaOpt.get();
                    if (passwordEncoder.matches(senhaDigitada, motorista.getSenha())) {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_MOTORISTA"));
                        // Não é mais preciso inicializar lazy aqui se toString() for sobrescrito para não incluir endereco
                        return new UsernamePasswordAuthenticationToken(motorista, senhaDigitada, authorities);
                    } else {
                        throw new BadCredentialsException("Senha incorreta.");
                    }
                }

                // 2. Se não for Motorista, tentar autenticar como EmpresaCliente
                Optional<EmpresaCliente> empresaOpt = empresaClienteService.findByEmail(email);
                if (empresaOpt.isPresent()) {
                    EmpresaCliente empresa = empresaOpt.get();
                    if (passwordEncoder.matches(senhaDigitada, empresa.getSenha())) {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_EMPRESA"));
                        // Não é mais preciso inicializar lazy aqui se toString() for sobrescrito para não incluir endereco
                        return new UsernamePasswordAuthenticationToken(empresa, senhaDigitada, authorities);
                    } else {
                        throw new BadCredentialsException("Senha incorreta.");
                    }
                }

                throw new BadCredentialsException("Usuário não encontrado.");
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

                // Armazena o objeto principal completo na sessão HTTP para fácil acesso em controllers e Thymeleaf
                request.getSession().setAttribute("loggedInUser", authentication.getPrincipal());

                if (authorities.contains(new SimpleGrantedAuthority("ROLE_MOTORISTA"))) {
                    response.sendRedirect("/motorista/home");
                } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_EMPRESA"))) {
                    response.sendRedirect("/empresa/home");
                } else {
                    response.sendRedirect("/login?error=true");
                }
            }
        };
    }
}
