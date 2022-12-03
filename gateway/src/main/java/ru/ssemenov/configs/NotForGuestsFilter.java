package ru.ssemenov.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class NotForGuestsFilter extends AbstractGatewayFilterFactory<NotForGuestsFilter.Config> {
    public NotForGuestsFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            UUID trace = exchange.getAttribute("trace");
            ServerHttpRequest request = exchange.getRequest();
            log.info("Start NotForGuestsFilter, request={}, trace={}", request, trace);
            if (!request.getHeaders().containsKey("username")) {
                log.error("Header username is not exist, authorization on gateway failed, error=401, trace={}", trace);
                return this.onError(exchange, "Not for guests", HttpStatus.UNAUTHORIZED);
            }
            log.error("Authorization on gateway is successful, trace={}", trace);
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}