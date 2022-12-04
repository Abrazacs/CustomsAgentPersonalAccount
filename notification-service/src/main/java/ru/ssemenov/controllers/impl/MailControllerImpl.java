package ru.ssemenov.controllers.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ssemenov.controllers.MailController;
import ru.ssemenov.services.MailService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
@Tag(name = "Сервис уведомлений", description = "Методы по работе с сервисом уведомлений")
public class MailControllerImpl implements MailController {

    private final MailService mailService;

    @Override
    @Operation(
            summary = "Запрос на получение списка рассылки по ИНН компании",
            responses = {
                    @ApiResponse(
                            description = "Получен список рассылки", responseCode = "200"
                    )
            }
    )
    @GetMapping
    public List<String> getMailingList(@RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode) {
        return mailService.getMailingList(vatCode);
    }

    @Override
    @Operation(
            summary = "Запрос на изменение списка рассылки по ИНН компании",
            responses = {
                    @ApiResponse(
                            description = "Список рассылки успешно изменен, возвращен список корректных адресов", responseCode = "200"
                    )
            }
    )
    @PostMapping
    public List<String> editMailingList(
            @RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode,
            @RequestBody @Parameter(description = "Список рассылки", required = true) List<String> emails) {
        return mailService.editMailingList(vatCode, emails);
    }

    @Override
    @Operation(
            summary = "Запрос на отправку писем пользователям по ИНН отправителя",
            responses = {
                    @ApiResponse(
                            description = "Почтовые уведомления отправлены", responseCode = "200"
                    )
            }
    )
    @GetMapping("/notify")
    public ResponseEntity<String> notifyMailRecipients(@RequestHeader @Parameter(description = "ИНН компании", required = true) String vatCode) {
        mailService.notifyMailRecipients(vatCode, "Mail title", "Hello, friend");
        return new ResponseEntity<>("Почтовые уведомления отправлены", HttpStatus.OK);
    }
}
