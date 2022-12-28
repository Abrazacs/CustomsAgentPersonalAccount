package ru.ssemenov.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import redis.clients.jedis.exceptions.JedisException;
import ru.ssemenov.configs.RabbitConfig;
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
    public void receiveMessage(String message) {
        UUID trace = UUID.randomUUID();
        String[] strings = message.split(" ");
        String vatCode = strings[0];
        String msg = String.format("Статус декларации № %s изменился на %s", strings[1], strings[2]);
        try {
            mailService.notifyMailRecipients(vatCode, TITLE, msg);
        } catch (MailException | JedisException e){
            log.error("Error while trying to send the message, trace={}, error message={}", trace, e.getMessage());
        }

    }
}
