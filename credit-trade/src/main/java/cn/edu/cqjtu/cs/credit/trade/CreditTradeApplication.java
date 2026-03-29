package cn.edu.cqjtu.cs.credit.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.AuthGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.DisputeGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.TradeGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.UserGrayLoadBalancerConfiguration;

/**
 * 交易服务启动类
 * @EnableDiscoveryClient 用于将服务注册到 Nacos
 */
@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClients(value = {
        @LoadBalancerClient(name = "credit-trade", configuration = TradeGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-user", configuration = UserGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-auth", configuration = AuthGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-dispute", configuration = DisputeGrayLoadBalancerConfiguration.class)
    }
)
public class CreditTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditTradeApplication.class, args);
        System.out.println("🚀 信用风险管理 - 交易服务启动成功！");
    }
}