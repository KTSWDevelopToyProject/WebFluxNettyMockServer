package com.kt.iotmakers.webfluxnettymockserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@Service
@Slf4j
public class MockService {

    /**
     * Mock Server 생성
     */
    public Mono<String> startMockServer() {

        return Mono.defer(() -> {
            DisposableServer server1 = HttpServer.create()
                    .host("localhost")
                    .port(8081)
                    .route(httpServerRoutes -> httpServerRoutes.get("/test",
                            (request, response) -> response.sendString(Mono.just("I am your 8081 test server !!!"))))
                    .bindNow();
            DisposableServer server2 = HttpServer.create()
                    .host("localhost")
                    .port(8082)
                    .route(httpServerRoutes -> httpServerRoutes.get("/test",
                            (request, response) -> response.sendString(Mono.just("I am your 8082 test server !!!"))))
                    .bindNow();
            DisposableServer server3 = HttpServer.create()
                    .host("localhost")
                    .port(8083)
                    .route(httpServerRoutes -> httpServerRoutes.get("/test",
                            (request, response) -> response.sendString(Mono.just("I am your 8083 test server !!!"))))
                    .bindNow();
            server1.onDispose();
            server2.onDispose();
            server3.onDispose();
            return Mono.just("Three test servers ard created !!!");
        }).subscribeOn(Schedulers.boundedElastic());
    }

}
