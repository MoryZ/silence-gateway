package com.old.silence.gateway.config;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author moryzang
 */
@Configuration
public class GatewayCustomConfig {

    /**
     * 基于用户的限流键解析器
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(
                        exchange.getRequest().getHeaders().getFirst("X-User-ID"))
                .defaultIfEmpty("anonymous"); // 如果没有用户ID，则使用匿名用户
    }

    /**
     * 基于IP的限流键解析器
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
