package com.old.silence.gateway.fallback;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moryzang
 */
@RestController
@RequestMapping
public class FallbackResource {

    /**
     * 配置服务的降级处理
     */
    @RequestMapping("/fallback/config")
    public String configFallback() {
        return "配置服务暂时不可用，请稍后再试！";
    }

    /**
     * 通用降级处理
     */
    @RequestMapping("/fallback/general")
    public String generalFallback() {
        return "服务暂时不可用，请稍后再试！";
    }
}