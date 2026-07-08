package com.obra.obra.controllers;

import com.obra.obra.dtos.request.LoginRequestDTO;
import com.obra.obra.dtos.response.LoginResponseDTO;
import com.obra.obra.repositories.UsuarioRepository;
import com.obra.obra.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return usuarioRepository.findByEmail(dto.getEmail())
                .filter(u -> encoder.matches(dto.getSenha(), u.getSenha()))
                .map(u -> ResponseEntity.ok(new LoginResponseDTO(jwtUtil.gerarToken(u.getEmail()))))
                .orElse(ResponseEntity.status(401).build());
    }
}
