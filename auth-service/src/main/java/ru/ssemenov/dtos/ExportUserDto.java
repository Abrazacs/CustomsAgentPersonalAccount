package ru.ssemenov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ExportUserDto {

    @NotNull
    @Schema(description = "идентификатор пользователя")
    private UUID id;

    @NotBlank
    @Schema(description = "логин", required = true)
    private String username;

    @Email
    @Schema(description = "e-mail пользователя", required = true, example = "mail@mail.com")
    private String email;
}
