package cn.edu.cqjtu.cs.credit.gateway.filter;

import cn.edu.cqjtu.cs.credit.common.entity.Constants;
import cn.edu.cqjtu.cs.credit.common.entity.Result;
import cn.edu.cqjtu.cs.credit.common.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.core.io.buffer.DataBuffer;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "") // 对应 YAML 顶级的 whitelist
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<String> whitelist = new ArrayList<>(); // 提供默认值防止 NPE

    // 必须提供 Setter 方法，否则 ConfigurationProperties 无法注入
    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 检查是否在白名单中
        if (isWhitelisted(path)) {
            System.out.println("请求路径在白名单中，无需认证：" + path);
            return chain.filter(exchange);
        }

        System.out.println("请求路径不在白名单中，需要认证：" + path);

        // 从Header中获取Token
        String token = extractToken(request);
        if (token == null) {
            return unauthorizedResponse(exchange, "未提供Token");
        }

        // 验证Token
        if (!jwtUtil.validateToken(token)) {
            return unauthorizedResponse(exchange, "Token无效");
        }

        // 解析Token
        Long userId = jwtUtil.getUserIdFromToken(token);
        String username = jwtUtil.getUsernameFromToken(token);

        // 检查Redis中是否存在该token
        String redisKey = Constants.REDIS_TOKEN_KEY_PREFIX + userId;
        return redisTemplate.hasKey(redisKey)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        // 添加Header
                        ServerHttpRequest mutatedRequest = request.mutate()
                                .header("X-User-Id", String.valueOf(userId))
                                .header("X-Username", username)
                                .build();
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    } else {
                        return unauthorizedResponse(exchange, "Token已失效或已注销");
                    }
                });
    }

    private boolean isWhitelisted(String path) {
        return whitelist.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        
        // 使用ObjectMapper序列化Result对象为JSON
        Result<Void> result = Result.error(401, message);
        try {
            String json = objectMapper.writeValueAsString(result);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            // 如果序列化失败，返回默认的错误信息
            String fallbackJson = "{\"code\":500,\"message\":\"服务器内部错误\",\"data\":null}";
            byte[] bytes = fallbackJson.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
    }

    private String extractToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}