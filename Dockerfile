# 1. 使用 JDK 21 运行环境
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# 2. 基础安全配置
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# 3. 复制 JAR 包
COPY --chown=appuser:appgroup target/silence-gateway-2.0.1-SNAPSHOT.jar app.jar

# 4. 配置环境变量（去掉末尾多余的反斜杠，并补全 Spring 规范的变量名）
ENV SPRING_PROFILES_ACTIVE=dev \
    NACOS_SERVER_ADDR=host.docker.internal:8848

# 5. 切换用户
USER appuser

# 6. 暴露网关端口
EXPOSE 9000

# 7. 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:9000/actuator/health || exit 1

# 8. 启动命令
ENTRYPOINT ["java", "-Xms256m", "-Xmx512m", "-XX:+UseG1GC", "-jar", "app.jar"]