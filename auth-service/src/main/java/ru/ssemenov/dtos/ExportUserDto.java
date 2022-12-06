package ru.ssemenov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class ExportUserDto {

    @NotBlank
    @Schema(description = "логин", required = true)
    private String username;

    @Email
    @Schema(description = "e-mail пользователя", required = true, example = "mail@mail.com")
    private String email;
}
