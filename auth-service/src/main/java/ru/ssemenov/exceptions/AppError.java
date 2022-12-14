package ru.ssemenov.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.ssemenov.dtos.Violation;

import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class AppError {
    @Schema(description = "Код ошибки", required = true, example = "RESOURCE_NOT_FOUND")
    private final String code;

    @Schema(description = "Текст ошибки", required = true,
            example = "This login is occupied. Try to use another one")
    private final String message;

    @Schema(description = "Список нарушений", required = true)
    private List<Violation> violations;
}
