package ru.ssemenov.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import ru.ssemenov.exceptions.AppError;
import ru.ssemenov.services.UserService;
import ru.ssemenov.utils.JwtTokenUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secure")
public class AuthController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    @Operation(
            summary = "Запрос на авторизацию",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = JwtResponse.class))
                    ),
                    @ApiResponse(
                            description = "Отказ в авторизации", responseCode = "401",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        User user = userService.findUserByUsername(authRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User with such login doesn't exist"));

        String token = jwtTokenUtil.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Operation(
            summary = "Запрос на добавление пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "Ошибка регистрации", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> addNewUser(
            @RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode,
            @Valid @RequestBody @Parameter(description = "Данные по пользователю", required = true) UserDto userDto) {
        userService.addUser(vatCode, userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(
            summary = "Запрос на удаление пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable @Parameter(description = "Идентификатор пользователя",
            required = true) UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Запрос на получение пользователей по ИНН компании",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "Пользователи не найдены", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/users")
    public List<ExportUserDto> getUsersByCompanyVAT(
            @RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode) {
       return userService.findUsersByCompanyVAT(vatCode);
    }
}
