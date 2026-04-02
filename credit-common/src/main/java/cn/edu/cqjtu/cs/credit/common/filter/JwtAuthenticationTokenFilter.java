package cn.edu.cqjtu.cs.credit.common.filter;

import cn.edu.cqjtu.cs.credit.common.entity.Constants;
import cn.edu.cqjtu.cs.credit.common.entity.login.LoginUser;
import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 业务微服务过滤器
 * 职责：从网关传过来的请求头中获取 X-User-Id，然后从共享 Redis 中捞出完整的权限信息塞给 Spring Security
 */
@Component
/**
 * Spring Cloud Gateway 是基于 WebFlux（底层是 Netty） 的，而你的 JwtAuthenticationTokenFilter 继承的 OncePerRequestFilter 是 Servlet（底层通常是 Tomcat） 的产物。
 * 在 WebFlux 环境下，jakarta.servlet.Filter 这个类根本不在 Classpath 里，所以网关启动扫描到这个 @Component 时直接原地爆炸。
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET) // 只有在 Servlet 环境下才实例化
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. 获取网关透传的用户ID（网关 AuthFilter 已经校验过 Token 了）
        String userId = request.getHeader("X-User-Id");

        // 2. 如果没有 ID，说明是白名单接口或者是网关漏拦截，直接放行交给 Security 默认处理（通常会报 403）
        if (!StringUtils.hasText(userId)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 从 Redis 中获取完整的 LoginUser (包含权限列表)
        // 注意：这里的 Key 要和网关/登录时存入的 Key 保持一致
        String redisKey = Constants.REDIS_TOKEN_KEY_PREFIX + userId;
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(redisKey);

        if (Objects.isNull(loginUser)) {
            // Redis 里没数据，说明登录失效了
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 关键：封装成 Security 认识的认证对象
        // 三个参数：用户信息、密码（null即可）、权限集合
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        // 5. 存入保安岗亭 (SecurityContextHolder)
        // 这样后续 Controller 里的 @PreAuthorize("hasAuthority('...')") 才能拿到数据
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 6. 放行
        filterChain.doFilter(request, response);
    }
}