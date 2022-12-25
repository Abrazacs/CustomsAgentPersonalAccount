package ru.ssemenov.controllers.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssemenov.controllers.CustomsDeclarationController;
import ru.ssemenov.converters.CustomsDeclarationConverter;
import ru.ssemenov.converters.PageConverter;
import ru.ssemenov.dtos.CustomsDeclarationRequest;
import ru.ssemenov.dtos.CustomsDeclarationResponse;
import ru.ssemenov.dtos.PageDto;
import ru.ssemenov.dtos.StatisticsResponse;
import ru.ssemenov.entities.CustomsDeclaration;
import ru.ssemenov.exceptions.AppError;
import ru.ssemenov.exceptions.ResourceException;
import ru.ssemenov.services.CustomsDeclarationServices;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/declarations")
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
                            content = @Content(schema = @Schema(implementation = CustomsDeclarationResponse.class))
                    )
            }
    )
    @GetMapping
    public PageDto<CustomsDeclarationResponse> getAllCustomsDeclarationByVatCode(
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
                            content = @Content(schema = @Schema(implementation = CustomsDeclarationResponse.class))
                    ),
                    @ApiResponse(
                            description = "Декларация не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public CustomsDeclarationResponse getCustomsDeclarationById(@PathVariable @Parameter(description = "Идентификатор декларации", required = true) UUID id) {
        return customsDeclarationConverter.entityToDto(customsDeclarationServices.findById(id));
    }

    @Override
    @Operation(
            summary = "Запрос на добавление новой декларации",
            responses = {
                    @ApiResponse(
                            description = "Декларация успешно создана", responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Ошибка валидации данных", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка сохранения декларации", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> addNewCustomsDeclaration(@Valid @RequestBody @Parameter(description = "Данные по таможенной декларации", required = true) CustomsDeclarationRequest customsDeclarationRequest) {
        UUID id = customsDeclarationServices.addCustomsDeclaration(customsDeclarationRequest);
        return ResponseEntity
                .created(URI.create(String.format("/%s", id)))
                .body(id);
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

    @Operation(
            summary = "Запрос на получение статистики за предыдущий месяц",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StatisticsResponse.class))
                    )
            }
    )
    @GetMapping("/statistic")
    @ResponseStatus(HttpStatus.OK)
    public StatisticsResponse getStatisticsByLastMonth(
            @RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode) {
        return customsDeclarationServices.getStatistics(vatCode);
    }

    @Override
    @Operation(
            summary = "Запрос на получение выписки деклараций по ИНН компании",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = InputStreamResource.class))
                    ),
                    @ApiResponse(
                            description = "Ошибка в получении файла", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = ResourceException.class))
                    )
            }
    )
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportCustomsDeclarations(@RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode) {
        File file = customsDeclarationServices.export(vatCode);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ResourceException("Файл не найден!");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "force-download"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=declarations.xlsx");
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers)
                .body(resource);
    }
}
