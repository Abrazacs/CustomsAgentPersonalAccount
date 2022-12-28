package ru.ssemenov.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationProducer {

    private static final String QUEUE_NAME = "Notifications";
    private final ConnectionFactory connectionFactory;

    public void publishNotification(String message) {
        UUID trace = UUID.randomUUID();
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME, false, false,false,null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            log.info("Message successfully published, trace={}", trace);
        }catch (IOException | TimeoutException e){
            log.error("Error while trying to establish connection, error={}, traceId={}", e.getMessage(), trace);
        }
    }


}
