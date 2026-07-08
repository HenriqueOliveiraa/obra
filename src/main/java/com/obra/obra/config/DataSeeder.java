package com.obra.obra.config;

import com.obra.obra.entities.Usuario;
import com.obra.obra.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${app.seed.usuarios[0].nome}")
    private String nome0;
    @Value("${app.seed.usuarios[0].email}")
    private String email0;
    @Value("${app.seed.usuarios[0].senha}")
    private String senha0;

    @Value("${app.seed.usuarios[1].nome}")
    private String nome1;
    @Value("${app.seed.usuarios[1].email}")
    private String email1;
    @Value("${app.seed.usuarios[1].senha}")
    private String senha1;

    @Override
    public void run(ApplicationArguments args) {
        criarSeNaoExiste(nome0, email0, senha0);
        criarSeNaoExiste(nome1, email1, senha1);
    }

    private void criarSeNaoExiste(String nome, String email, String senha) {
        if (!usuarioRepository.existsByEmail(email)) {
            usuarioRepository.save(new Usuario(null, nome, email, encoder.encode(senha)));
        }
    }
}
