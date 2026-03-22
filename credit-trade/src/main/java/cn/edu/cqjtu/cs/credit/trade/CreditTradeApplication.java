package cn.edu.cqjtu.cs.credit.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 交易服务启动类
 * @EnableDiscoveryClient 用于将服务注册到 Nacos
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CreditTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditTradeApplication.class, args);
        System.out.println("🚀 信用风险管理 - 交易服务启动成功！");
    }
}