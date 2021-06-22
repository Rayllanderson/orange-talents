package com.rayllanderson.forum.controllers;

import com.rayllanderson.forum.controllers.dto.LoginDto;
import com.rayllanderson.forum.controllers.form.LoginForm;
import com.rayllanderson.forum.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @Autowired
    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<LoginDto> autenticar(@RequestBody @Valid LoginForm form){
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();
        try {
            Authentication authenticate = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authenticate);
            return ResponseEntity.ok(new LoginDto(token));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
