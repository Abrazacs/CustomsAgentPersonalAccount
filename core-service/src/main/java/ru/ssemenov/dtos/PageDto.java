package ru.ssemenov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<E> {
    @Schema(description = "Список объектов на странице")
    private List<E> items;

    @Schema(description = "Номер страницы", required = true, example = "1")
    private int page;

    @Schema(description = "Общее количество страниц", required = true, example = "3")
    private int totalPages;

    @Schema(description = "Общее количество продуктов", required = true, example = "13")
    private long totalElements;

    @Schema(description = "Количество товаров на странице", required = true, example = "5")
    private int numberOfElements;

    @Schema(description = "Количество выводимых товаров на странице", required = true, example = "5")
    private int pageSize;
}
