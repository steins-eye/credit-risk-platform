package cn.edu.cqjtu.cs.credit.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.AuthGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.DisputeGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.TradeGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.UserGrayLoadBalancerConfiguration;

@SpringBootApplication(scanBasePackages = {
        "cn.edu.cqjtu.cs.credit.auth",
        "cn.edu.cqjtu.cs.credit.common",
})
@EnableDiscoveryClient
@EnableFeignClients
@LoadBalancerClients(value = {
        @LoadBalancerClient(name = "credit-trade", configuration = TradeGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-user", configuration = UserGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-auth", configuration = AuthGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-dispute", configuration = DisputeGrayLoadBalancerConfiguration.class)
    }
)
public class CreditAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditAuthApplication.class, args);
    }

}