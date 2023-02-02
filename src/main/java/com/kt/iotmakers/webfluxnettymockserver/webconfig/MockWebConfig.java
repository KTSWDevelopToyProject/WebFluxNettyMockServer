package com.kt.iotmakers.webfluxnettymockserver.webconfig;

import com.kt.iotmakers.webfluxnettymockserver.handler.MockHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
public class MockWebConfig implements WebFluxConfigurer {

    private final MockHandler mockHandler;

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("/mock", builder -> builder
                                .POST("/start", mockHandler::startMockServer)
                )
                .build();
    }

}
