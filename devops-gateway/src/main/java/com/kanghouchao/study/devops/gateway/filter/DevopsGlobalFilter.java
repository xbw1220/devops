package com.kanghouchao.study.devops.gateway.filter;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author kanghouchao
 */
@Component
@AllArgsConstructor
public class DevopsGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("===DevopsGlobalFilter.filter===");
        System.out.println(String.format("===%s===", exchange.getRequest().getPath()));
        Mono<Void> filter = chain.filter(exchange);
        System.out.println(String.format("===%d===", exchange.getResponse().getStatusCode().value()));
        return filter;
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
