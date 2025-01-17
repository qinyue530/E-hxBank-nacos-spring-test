package com.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class GatewayTokenFilter implements GlobalFilter {
    @Value("${server.port}")
    private String serverPort;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String str = exchange.getRequest().getQueryParams().getFirst("token");
        str = "1" ;
        if(StringUtils.isEmpty(str)){
            System.out.println("没有token  ==================================");
            //自定义返回页面 显示信息
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            String msg = "token is null";
            DataBuffer buffer = response.bufferFactory().wrap(msg.getBytes());
            return response.writeWith(Mono.just(buffer));
        }
        ServerHttpRequest request = exchange.getRequest().mutate().header("serverPort",serverPort).build();
        return chain.filter(exchange.mutate().request(request).build());
    }
}
