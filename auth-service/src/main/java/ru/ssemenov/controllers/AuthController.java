package ru.ssemenov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import ru.ssemenov.dtos.JwtRequest;
import ru.ssemenov.dtos.JwtResponse;
import ru.ssemenov.services.UserService;
import utils.JwtTokenUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class AuthController {

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
