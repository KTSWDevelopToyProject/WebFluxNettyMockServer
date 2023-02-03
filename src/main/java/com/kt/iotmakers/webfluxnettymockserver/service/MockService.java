package com.kt.iotmakers.webfluxnettymockserver.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class MockService {

    private DisposableServer server;
    private List<ConcurrentHashMap<String, DisposableServer>> servers;

    @PostConstruct
    public void init() {
        this.servers = new ArrayList<>();
    }

    /**
     * Mock server 목록 조회
     */
    public Mono<List<ConcurrentHashMap<String, DisposableServer>>> retrieveMockServers() {
        return Mono.just(servers);
    }

    /**
     * Mock Server 생성
     */
    public Mono<String> startMockServer(int port) {
        return Mono.defer(() -> {
            String strPort = String.valueOf(port);
            if (servers.stream().anyMatch(server -> server.containsKey(strPort))) {
                return Mono.just(String.format("요청하신 port 는 다른 서버가 이미 사용중이라 신규 생성이 불가합니다 !!!"));
            }

            this.server = HttpServer.create()
                    .host("localhost")
                    .port(port)
                    .route(httpServerRoutes -> httpServerRoutes.get("/test",
                            (request, response) -> response.sendString(Mono.just(String.format("I am your %d port HTTP server !!!", port)))))
                    .bindNow();
            this.server.onDispose();

            ConcurrentHashMap<String, DisposableServer> newServer = new ConcurrentHashMap<>();
            newServer.put(strPort, this.server);
            this.servers.add(newServer);
            return Mono.just(String.format("The %d port HTTP server is created !!!", port));
        }).subscribeOn(Schedulers.boundedElastic()).log("===== server has been started =====");
    }

    /**
     * Mock Server Stop
     */
    public Mono<String> stopMockServer(int port) {
        return Mono.defer(
                        () -> {
                            String strPort = String.valueOf(port);
                            if (!servers.stream().anyMatch(server -> server.containsKey(strPort))) {
                                return Mono.just("셧다운 요청하신 서버가 존재하지 않습니다. 다시 확인 바랍니다.");
                            }

                            ConcurrentHashMap<String, DisposableServer> targetedServerMap =
                                    servers.stream().filter(server -> server.containsKey(strPort))
                                    .findAny().get();
                            targetedServerMap.get(strPort).disposeNow();
                            servers.remove(targetedServerMap);

                            return Mono.just(String.format("The %d port HTTP server has been shut down", port));
                        }
                )
                .subscribeOn(Schedulers.boundedElastic()).log("===== server is stopped =====");
    }

}
