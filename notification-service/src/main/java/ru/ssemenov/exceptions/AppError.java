package ru.ssemenov.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppError {
    @Schema(description = "Код ошибки", required = true, example = "MAIL_EXCEPTION")
    private String code;

    @Schema(description = "Текст ошибки", required = true, example = "Список получателей пуст!")
    private String message;
}
