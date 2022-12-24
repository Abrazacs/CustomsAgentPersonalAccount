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

    @NotBlank (message = "Поле логин обязательно для заполнения")
    @Schema(description = "логин пользователя", required = true)
    private String username;

    @NotBlank (message = "Поле пароль не должно быть пустым")
    @Schema(description = "пароль", required = true)
    private String password;


    @Email (message = "Поле e-mail должно соотвествовать формату mail@mail.com")
    @NotBlank (message = "Поле e-mail не должно быть пустым")
    @Schema(description = "email", required = true, example = "mail@mail.com")
    private String email;

    @NotEmpty(message = "Необходимо выбрать роль")
    @Schema(description = "список ролей пользователя", required = true)
    private List<String> rolesNames;
}
