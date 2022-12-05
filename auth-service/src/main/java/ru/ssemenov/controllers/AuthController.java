package ru.ssemenov.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import ru.ssemenov.dtos.ExportUserDto;
import ru.ssemenov.dtos.JwtRequest;
import ru.ssemenov.dtos.JwtResponse;
import ru.ssemenov.dtos.UserDto;
import ru.ssemenov.entities.User;
import ru.ssemenov.services.UserService;
import ru.ssemenov.utils.JwtTokenUtil;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secure")
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        User user = userService.findUserByUsername(authRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User with such login doesn't exist"));

        String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(
            @RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode,
            @RequestBody @Parameter(description = "Данные по пользователю", required = true) UserDto userDto) {
        userService.addUser(vatCode, userDto);
        return new ResponseEntity<>(String.format("Пользователь %s зарегистрирован!", userDto.getUsername()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable @Parameter(description = "Идентификатор пользователя",
            required = true) UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(String.format("Пользователь %s удален!", id), HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<ExportUserDto> getUsersByCompanyVAT(@RequestParam @Parameter(description = "ИНН компании", required = true) String vatCode) {
       return userService.findUsersByCompanyVAT(vatCode);
    }
}
