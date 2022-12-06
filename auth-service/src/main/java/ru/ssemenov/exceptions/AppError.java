package ru.ssemenov.exceptions;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppError {
    @Schema(description = "Код ошибки", required = true, example = "RESOURCE_NOT_FOUND")
    private String code;

    @Schema(description = "Текст ошибки", required = true,
            example = "Декларация с id=684e4ea4-cac9-4b33-843e-d26274ff9f7e не существует!")
    private String message;

}
