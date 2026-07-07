package com.obra.obra.controllers;

import com.obra.obra.dtos.request.LoginRequestDTO;
import com.obra.obra.dtos.response.LoginResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()
                || dto.getSenha() == null || dto.getSenha().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new LoginResponseDTO("token-obra-" + System.currentTimeMillis()));
    }
}
