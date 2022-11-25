package ru.ssemenov.controllers.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssemenov.controllers.CustomsDeclarationController;
import ru.ssemenov.converters.CustomsDeclarationConverter;
import ru.ssemenov.converters.PageConverter;
import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.dtos.PageDto;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.AppError;
import ru.ssemenov.services.CustomsDeclarationServices;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/declarations")
@RequiredArgsConstructor
@Tag(name = "Таможенные декларации", description = "Методы по работе с таможенными декларациями")
public class CustomsDeclarationControllerImpl implements CustomsDeclarationController {

    private final CustomsDeclarationServices customsDeclarationServices;
    private final CustomsDeclarationConverter customsDeclarationConverter;
    private final PageConverter pageConverter;

    @Override
    @Operation(
            summary = "Запрос на получение списка деклараций по ИНН компании",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CustomsDeclarationDto.class))
                    )
            }
    )
    @GetMapping
    public PageDto<CustomsDeclarationDto> getAllCustomsDeclarationByVatCode(
            @RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode,
            @RequestParam(name = "page", defaultValue = "0", required = false) @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "50", required = false) @Parameter(description = "Кол-во выводимых элементов на странице") Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "number", required = false) @Parameter(description = "Сортировка по имени столбца") String sortBy,
            @RequestParam(name = "numberPart", required = false) @Parameter(description = "Фильтр по номеру декларации") String numberPart) {
        if (page < 1) {
            page = 1;
        }
        Page<CustomsDeclaration> customsDeclarations = customsDeclarationServices.findAll(vatCode, page - 1, pageSize, sortBy, numberPart);
        return pageConverter.entityToDto(customsDeclarations.map(customsDeclarationConverter::entityToDto));
    }

    @Override
    @Operation(
            summary = "Запрос на получение декларации по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CustomsDeclarationDto.class))
                    ),
                    @ApiResponse(
                            description = "Декларация не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public CustomsDeclarationDto getCustomsDeclarationById(@PathVariable @Parameter(description = "Идентификатор декларации", required = true) UUID id) {
        return customsDeclarationConverter.entityToDto(customsDeclarationServices.findById(id));
    }

    @Override
    @Operation(
            summary = "Запрос на добавление новой декларации",
            responses = {
                    @ApiResponse(
                            description = "Декларация успешно создана", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addNewCustomsDeclaration(@RequestBody @Parameter(description = "Данные по таможенной декларации", required = true) CustomsDeclarationDto customsDeclarationDto) {
        customsDeclarationServices.addCustomsDeclaration(customsDeclarationDto);
        return new ResponseEntity<>("Декларация " + customsDeclarationDto.getNumber() + " была успешно добавлена", HttpStatus.CREATED);
    }

    @Override
    @Operation(
            summary = "Запрос на удаление декларации по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Декларация не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomsDeclarationById(@PathVariable @Parameter(description = "Идентификатор декларации", required = true) UUID id) {
        customsDeclarationServices.deleteById(id);
        return new ResponseEntity<>("Декларация c id:" + id + " была успешно удалена", HttpStatus.OK);
    }
}