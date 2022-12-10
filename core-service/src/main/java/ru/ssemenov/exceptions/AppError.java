package ru.ssemenov.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.ssemenov.dtos.Violation;

import java.util.List;

@Data
@AllArgsConstructor
public class AppError {
    public AppError(String code, String message) {
        new AppError(code, message, null);
    }

    @Schema(description = "Код ошибки", required = true, example = "RESOURCE_NOT_FOUND")
    private String code;

    @Schema(description = "Текст ошибки", required = true, example = "Декларация с id=684e4ea4-cac9-4b33-843e-d26274ff9f7e не существует!")
    private String message;

    @Schema(description = "Список нарушений", required = true)
    private List<Violation> violations;
}
