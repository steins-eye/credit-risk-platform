package cn.edu.cqjtu.cs.credit.auth.controller;

import cn.edu.cqjtu.cs.credit.auth.feign.UserFeignClient;
import cn.edu.cqjtu.cs.credit.common.util.JwtUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import cn.edu.cqjtu.cs.credit.auth.util.PasswordUtil;
import cn.edu.cqjtu.cs.credit.common.entity.Result;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.AssertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String REDIS_TOKEN_KEY_PREFIX = "auth:token:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        // 获取用户信息
        Result<Map<String, Object>> userResult = userFeignClient.getByUsername(loginRequest.getUsername());
        if (userResult.getCode() != 200 || userResult.getData() == null) {
            return Result.error("用户名或密码错误");
        }
        Map<String, Object> userMap = userResult.getData();
        // 提取用户字段
        Long userId = ((Number) userMap.get("userId")).longValue();
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        // 验证密码
        if (!passwordUtil.matches(loginRequest.getPassword(), password)) {
            return Result.error("用户名或密码错误");
        }
        // 生成token
        String token = jwtUtil.generateToken(userId, username, ""); // roleKeys暂时为空
        // 存储token到Redis
        String redisKey = REDIS_TOKEN_KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(redisKey, token, jwtUtil.getExpiration(), java.util.concurrent.TimeUnit.MILLISECONDS);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", userMap);
        return Result.success(data);
    }

    @PostMapping("/validate")
    public Result<Map<String, Object>> validate(@RequestBody ValidateRequest validateRequest) {
        String token = validateRequest.getToken();
        if (!jwtUtil.validateToken(token)) {
            return Result.error("token无效");
        }
        Claims claims = jwtUtil.parseToken(token);
        Map<String, Object> data = new HashMap<>();
        data.put("userId", claims.get("userId", Long.class));
        data.put("username", claims.get("username", String.class));
        data.put("roleKeys", claims.get("roleKeys", String.class));
        return Result.success(data);
    }

    static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class ValidateRequest {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestBody ValidateRequest request) {
        String token = request.getToken();
        if (!jwtUtil.validateToken(token)) {
            return Result.error("token无效");
        }
        Long userId = jwtUtil.getUserIdFromToken(token);
        String redisKey = REDIS_TOKEN_KEY_PREFIX + userId;
        redisTemplate.delete(redisKey);
        return Result.success();
    }
}