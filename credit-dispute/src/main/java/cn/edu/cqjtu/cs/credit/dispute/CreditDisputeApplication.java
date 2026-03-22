package cn.edu.cqjtu.cs.credit.dispute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 纠纷服务启动类
 * @EnableDiscoveryClient 用于将服务注册到 Nacos
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CreditDisputeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditDisputeApplication.class, args);
        System.out.println("🚀 信用风险管理 - 纠纷服务启动成功！");
    }
}