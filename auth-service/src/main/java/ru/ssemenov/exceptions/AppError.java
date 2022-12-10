package ru.ssemenov.exceptions;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ssemenov.dtos.Violation;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppError {
    public AppError(String code, String message) {
        new AppError(code, message, null);
    }

    @Schema(description = "Код ошибки", required = true, example = "RESOURCE_NOT_FOUND")
    private String code;

    @Schema(description = "Текст ошибки", required = true,
            example = "This login is occupied. Try to use another one")
    private String message;

    @Schema(description = "Список нарушений", required = true)
    private List<Violation> violations;
}
