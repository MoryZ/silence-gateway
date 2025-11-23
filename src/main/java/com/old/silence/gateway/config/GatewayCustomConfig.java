package com.old.silence.gateway.config;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author moryzang
 */
@Configuration
public class GatewayCustomConfig {

    private static final String X_User_ID = "X-User-ID";

    /**
     * 基于用户的限流键解析器
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(
                        exchange.getRequest().getHeaders().getFirst(X_User_ID))
                .defaultIfEmpty("anonymous"); // 如果没有用户ID，则使用匿名用户
    }

    /**
     * 基于IP的限流键解析器
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }
}
