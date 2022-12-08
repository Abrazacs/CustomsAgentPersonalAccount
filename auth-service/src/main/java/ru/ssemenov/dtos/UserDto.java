package ru.ssemenov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {

    @NotBlank
    @Schema(description = "логин пользователя", required = true)
    private String username;

    @NotBlank
    @Schema(description = "пароль", required = true)
    private String password;

    @NotBlank
    @Email
    @Schema(description = "email", required = true, example = "mail@mail.com")
    private String email;

    @NotEmpty(message = "необходимо выбрать роль")
    @Schema(description = "список ролей пользователя", required = true)
    private List<String> rolesNames;
}
