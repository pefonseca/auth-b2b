package com.pefonseca.auth.controller;

import com.pefonseca.auth.domain.user.AuthenticationDTO;
import com.pefonseca.auth.domain.user.RegisterDTO;
import com.pefonseca.auth.domain.user.User;
import com.pefonseca.auth.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return null;
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDTO registerDTO) {
        if(this.userRepository.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User newUser = User.builder().login(registerDTO.login()).password(encryptedPassword).role(registerDTO.role()).build();

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
