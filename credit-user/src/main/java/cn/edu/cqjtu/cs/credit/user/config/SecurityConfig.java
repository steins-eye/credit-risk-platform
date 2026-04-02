package cn.edu.cqjtu.cs.credit.user.config;

import cn.edu.cqjtu.cs.credit.common.config.security.BaseSecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * 业务微服务安全配置类
 * 职责：关闭不必要的功能（如 Session），并把过滤器插到正确的位置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 开启 @PreAuthorize 注解支持
public class SecurityConfig extends BaseSecurityConfig {

    // 关键：手动注入 AuthenticationConfiguration
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * 手动将 AuthenticationManager 暴露为 Bean
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}