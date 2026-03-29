package cn.edu.cqjtu.cs.credit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.TradeGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.UserGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.AuthGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.DisputeGrayLoadBalancerConfiguration;

@SpringBootApplication(scanBasePackages = {
        "cn.edu.cqjtu.cs.credit.gateway",
        "cn.edu.cqjtu.cs.credit.common"
})
@EnableDiscoveryClient
// 关键：在这里直接引用内部的配置类
@LoadBalancerClients(value = {
        @LoadBalancerClient(name = "credit-trade", configuration = TradeGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-user", configuration = UserGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-auth", configuration = AuthGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-dispute", configuration = DisputeGrayLoadBalancerConfiguration.class)
    }
)
public class CreditGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditGatewayApplication.class, args);
    }

}