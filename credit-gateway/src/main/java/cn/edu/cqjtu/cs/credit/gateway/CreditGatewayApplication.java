package cn.edu.cqjtu.cs.credit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API网关启动类
 * @EnableDiscoveryClient 用于将网关注册到 Nacos
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CreditGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditGatewayApplication.class, args);
        System.out.println("🚀 信用风险管理 - API网关启动成功！");
    }
}