package ru.ssemenov.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import ru.ssemenov.exceptions.MailException;
import ru.ssemenov.services.impl.MailServiceImpl;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitConsumer {

    private static final String TITLE = "Notification";
    public final MailServiceImpl mailService;

    @RabbitListener(queues = "Notifications")
    public void receiveMessage(String message){
        UUID trace = UUID.randomUUID();
        String[] strings = message.split(" ");
        String vatCode = strings[0];
        String msg = String.format("Статус декларации № %s изменился на %s", strings[1], strings[2]);
        try {
            mailService.notifyMailRecipients(vatCode, TITLE, msg);
        }catch (Exception e){
            log.info("Error occurred while trying to send the message, trace={} ", trace);
        }

    }

}
