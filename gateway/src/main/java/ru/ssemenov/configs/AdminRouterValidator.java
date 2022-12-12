package ru.ssemenov.configs;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AdminRouterValidator {

    public static final List<String> notAdminApiEndpoints = List.of(
            "/api/v1/secure/authenticate"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> notAdminApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}