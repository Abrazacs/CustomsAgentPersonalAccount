package ru.ssemenov.utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationProducer {

    private static final String NOTIFICATION_QUEUE = "notifications";
    private final RabbitTemplate rabbitTemplate;

    public void publishNotification(String message) {
        UUID trace = UUID.randomUUID();
        log.info("Start send message to notification, traceId={}", trace);
        rabbitTemplate.convertAndSend(NOTIFICATION_QUEUE, message);
        log.info("Message successfully published, trace={}", trace);
    }
}
