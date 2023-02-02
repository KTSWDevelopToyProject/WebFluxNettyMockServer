package com.kt.iotmakers.webfluxnettymockserver.handler;

import com.kt.iotmakers.webfluxnettymockserver.service.MockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MockHandler {

    private final MockService mockService;

    public Mono<ServerResponse> startMockServer(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mockService.startMockServer(), String.class);
    }

}
