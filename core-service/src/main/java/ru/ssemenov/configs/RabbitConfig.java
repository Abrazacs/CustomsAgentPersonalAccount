package ru.ssemenov.configs;

import com.rabbitmq.client.ConnectionFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@AllArgsConstructor
public class RabbitConfig {

    @Bean
    ConnectionFactory connectionFactory(){
        return new ConnectionFactory();
    }

}
