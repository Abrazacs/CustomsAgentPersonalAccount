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

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class AdminFilter extends AbstractGatewayFilterFactory<AdminFilter.Config> {
    private final AdminRouterValidator adminRouterValidator;

    public AdminFilter(AdminRouterValidator adminRouterValidator) {
        super(Config.class);
        this.adminRouterValidator = adminRouterValidator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            UUID trace = exchange.getAttribute("trace");
            ServerHttpRequest request = exchange.getRequest();
            log.info("Start AdminFilter, request={}, trace={}", request.getURI().getPath(), trace);
            if (adminRouterValidator.isSecured.test(request)) {
                if (!request.getHeaders().containsKey("username")) {
                    log.error("Header username is not exist, authorization on gateway failed, error=401, trace={}", trace);
                    return this.onError(exchange, "Not for guests", HttpStatus.UNAUTHORIZED);
                }
                log.info("Request to protected for admin endpoint, start search admin role, trace={}", trace);
                if (request.getHeaders().containsKey("roles")) {
                    List<String> roles = request.getHeaders().get("roles");
                    log.info("Roles={} found for user={} ", roles.get(0), request.getHeaders().get("username"));
                    if (!roles.get(0).contains("ROLE_ADMIN")) {
                        log.info("User={} don't have ADMIN role, access is denied trace={}", request.getHeaders().get("username"), trace);
                        return this.onError(exchange, "Only for admin", HttpStatus.UNAUTHORIZED);
                    }
                }
            }
            log.info("Authorization on gateway is successful, trace={}", trace);
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