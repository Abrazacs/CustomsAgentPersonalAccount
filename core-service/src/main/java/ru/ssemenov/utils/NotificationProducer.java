package ru.ssemenov.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.ssemenov.dtos.Notification;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationProducer {

    private static final String NOTIFICATION_QUEUE = "notifications";
    private final RabbitTemplate rabbitTemplate;

    public void publishNotification(Notification notification) {
        UUID trace = UUID.randomUUID();
        log.info("Start send message to notification, trace={}", trace);
        rabbitTemplate.convertAndSend(NOTIFICATION_QUEUE, notification);
        log.info("Message successfully published, trace={}", trace);
    }
}
