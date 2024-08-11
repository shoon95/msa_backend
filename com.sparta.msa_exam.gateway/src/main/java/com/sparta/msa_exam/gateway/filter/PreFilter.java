package com.sparta.msa_exam.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PreFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    HttpHeaders headers = exchange.getResponse().getHeaders();
                    String serverPort = exchange.getRequest().getLocalAddress().getPort() + "";
                    headers.add("server-Port", serverPort);
                }
        ));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
