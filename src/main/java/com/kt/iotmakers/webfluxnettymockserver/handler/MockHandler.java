package com.kt.iotmakers.webfluxnettymockserver.handler;

import com.kt.iotmakers.webfluxnettymockserver.service.MockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MockHandler {

    private final MockService mockService;

    /**
     * Mock server 목록 조회
     */
    public Mono<ServerResponse> retrieveMockServers(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mockService.retrieveMockServers(), List.class);
    }

    /**
     * Mock Server 생성
     */
    public Mono<ServerResponse> startMockServer(ServerRequest request) {
        int port = Integer.parseInt(request.pathVariable("port"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mockService.startMockServer(port), String.class);
    }

    /**
     * Mock Server Stop
     */
    public Mono<ServerResponse> stopMockServer(ServerRequest request) {
        int port = Integer.parseInt(request.pathVariable("port"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mockService.stopMockServer(port), String.class);
    }

}
