package org.example.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class ResourceRouter {

    @Bean
    public RouterFunction<ServerResponse> resourceRoutes() {
        return RouterFunctions
                .route()
                .GET("/resources/user", this::readUser)
                .build();
    }

    public Mono<ServerResponse> readUser(ServerRequest request) {
        return request.principal()
                .cast(Authentication.class)
                .map(auth -> "The user can read." + auth.getName() + auth.getAuthorities())
                .flatMap(message -> ServerResponse.ok().bodyValue(message));
    }
}
