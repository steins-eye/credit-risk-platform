package cn.edu.cqjtu.cs.credit.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;

import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.AuthGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.DisputeGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.TradeGrayLoadBalancerConfiguration;
import cn.edu.cqjtu.cs.credit.common.config.loadbalancer.UserGrayLoadBalancerConfiguration;

@SpringBootApplication
@MapperScan("cn.edu.cqjtu.cs.credit.user.mapper")
@LoadBalancerClients(value = {
        @LoadBalancerClient(name = "credit-trade", configuration = TradeGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-user", configuration = UserGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-auth", configuration = AuthGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-dispute", configuration = DisputeGrayLoadBalancerConfiguration.class)
    }
)
public class CreditUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditUserApplication.class, args);
    }
}