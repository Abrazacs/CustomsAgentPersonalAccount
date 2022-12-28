package ru.ssemenov.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

import ru.ssemenov.dto.Notification;
import ru.ssemenov.exceptions.MailException;
import ru.ssemenov.services.impl.MailServiceImpl;

import java.util.UUID;

import static ru.ssemenov.configs.RabbitConfig.NOTIFICATION_QUEUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitConsumer {

    private static final String TITLE = "Notification";
    public final MailServiceImpl mailService;

    @RabbitListener(queues = NOTIFICATION_QUEUE)
    public void receiveMessage(Notification notification) {
        UUID trace = UUID.randomUUID();
        try {
            mailService.notifyMailRecipients(notification.getVatCode(), TITLE, notification.getMessage());
        } catch (MailException | RedisConnectionFailureException e) {
            log.error("Error while trying to send the email message, error={}, trace={}", e.getMessage(), trace);
        }
    }
}
