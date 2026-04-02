package cn.edu.cqjtu.cs.credit.product.config;

import cn.edu.cqjtu.cs.credit.common.config.security.BaseSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * 商品服务安全配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // 开启 @PreAuthorize 注解支持
public class SecurityConfig extends BaseSecurityConfig {

}