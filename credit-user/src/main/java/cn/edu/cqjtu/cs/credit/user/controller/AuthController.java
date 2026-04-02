package cn.edu.cqjtu.cs.credit.user.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.cqjtu.cs.credit.common.entity.Constants;
import cn.edu.cqjtu.cs.credit.common.entity.Result;
import cn.edu.cqjtu.cs.credit.common.entity.dto.LoginDTO;
import cn.edu.cqjtu.cs.credit.common.entity.login.LoginUser;
import cn.edu.cqjtu.cs.credit.common.entity.po.SysUser;
import cn.edu.cqjtu.cs.credit.common.util.JwtUtil;
import cn.edu.cqjtu.cs.credit.user.entity.dto.RegisterDTO;
import cn.edu.cqjtu.cs.credit.user.entity.dto.ValidateDTO;
import cn.edu.cqjtu.cs.credit.user.service.SysUserService;
import cn.edu.cqjtu.cs.credit.user.util.PasswordUtil;
import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate; // 改为 Object

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        // 1. 封装认证对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword());

        // 2. 执行认证（会调用你的 UserDetailsService）
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 3. 认证成功，获取 LoginUser
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userId = loginUser.getSysUser().getId();

        // 4. 使用你的 JwtUtil 生成 Token
        String token = jwtUtil.generateToken(userId, loginUser.getUsername()); 

        // 5. 将整个 LoginUser 存入 Redis，方便后续过滤器获取权限
        // 注意：LoginUser 必须实现 Serializable 接口
        String redisKey = Constants.REDIS_TOKEN_KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(redisKey, loginUser, jwtUtil.getExpiration(), TimeUnit.MILLISECONDS);

        return Result.success(Map.of("token", token));
    }

    @PostMapping("/validate")
    public Result<Map<String, Object>> validate(@RequestBody ValidateDTO validateDTO) {
        String token = validateDTO.getToken();
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

    @PostMapping("/register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        SysUser existingUser = sysUserService.lambdaQuery().eq(SysUser::getUsername, registerDTO.getUsername())
                .one();
        if (existingUser != null) {
            return Result.error("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (registerDTO.getEmail() != null && !registerDTO.getEmail().isEmpty()) {
            SysUser existingEmail = sysUserService.lambdaQuery().eq(SysUser::getEmail, registerDTO.getEmail())
                    .one();
            if (existingEmail != null) {
                return Result.error("邮箱已被注册");
            }
        }

        // 创建新用户
        SysUser newUser = new SysUser();
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(passwordUtil.encode(registerDTO.getPassword()));
        newUser.setNickname(registerDTO.getNickname());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPhone(registerDTO.getPhone());
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setUpdateTime(LocalDateTime.now());

        boolean saved = sysUserService.save(newUser);
        if (saved) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败，请稍后重试");
        }
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestBody ValidateDTO validateDTO) {
        String token = validateDTO.getToken();
        if (!jwtUtil.validateToken(token)) {
            return Result.error("token无效");
        }
        Long userId = jwtUtil.getUserIdFromToken(token);
        String redisKey = Constants.REDIS_TOKEN_KEY_PREFIX + userId;
        redisTemplate.delete(redisKey);
        return Result.success();
    }

}
