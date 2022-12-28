package ru.ssemenov.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import ru.ssemenov.services.impl.MailServiceImpl;

@Component
@RequiredArgsConstructor
public class RabbitConsumer {

    private static final String TITLE = "Notification";
    public final MailServiceImpl mailService;

    @RabbitListener(queues = "Notifications")
    public void receiveMessage(String message){
        String[] strings = message.split(" ");
        String vatCode = strings[0];
        String msg = String.format("Статус декларации № %s изменился на %s", strings[1], strings[2]);
        mailService.notifyMailRecipients(vatCode, TITLE, msg);
    }

}
