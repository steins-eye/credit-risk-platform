package cn.edu.cqjtu.cs.credit.gateway;

import cn.edu.cqjtu.cs.credit.gateway.config.GrayLoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {
        "cn.edu.cqjtu.cs.credit.gateway",
        "cn.edu.cqjtu.cs.credit.common"
})
@EnableDiscoveryClient
// 关键：在这里直接引用内部的配置类
@LoadBalancerClients(value = {
        @LoadBalancerClient(name = "credit-auth", configuration = CreditGatewayApplication.AuthGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-dispute", configuration = CreditGatewayApplication.DisputeGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-trade", configuration = CreditGatewayApplication.TradeGrayLoadBalancerConfiguration.class),
        @LoadBalancerClient(name = "credit-user", configuration = CreditGatewayApplication.UserGrayLoadBalancerConfiguration.class),
    }
)
public class CreditGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditGatewayApplication.class, args);
    }

    @Configuration(proxyBeanMethods = false)
    public static class AuthGrayLoadBalancerConfiguration {

        @Bean
        ReactorLoadBalancer<ServiceInstance> authGrayLoadBalancer(
                LoadBalancerClientFactory loadBalancerClientFactory) {
            return new GrayLoadBalancer(
                    loadBalancerClientFactory.getLazyProvider("credit-auth", ServiceInstanceListSupplier.class),
                    "credit-auth");
        }
    }

    @Configuration(proxyBeanMethods = false)
    public static class DisputeGrayLoadBalancerConfiguration {

        @Bean
        ReactorLoadBalancer<ServiceInstance> disputeGrayLoadBalancer(
                LoadBalancerClientFactory loadBalancerClientFactory) {
            return new GrayLoadBalancer(
                    loadBalancerClientFactory.getLazyProvider("credit-dispute", ServiceInstanceListSupplier.class),
                    "credit-dispute");
        }
    }

    @Configuration(proxyBeanMethods = false)
    public static class TradeGrayLoadBalancerConfiguration {
        

        @Bean
        ReactorLoadBalancer<ServiceInstance> tradeGrayLoadBalancer(
                LoadBalancerClientFactory loadBalancerClientFactory) {
            return new GrayLoadBalancer(
                    loadBalancerClientFactory.getLazyProvider("credit-trade", ServiceInstanceListSupplier.class),
                    "credit-trade");
        }
    }

    @Configuration(proxyBeanMethods = false)
    public static class UserGrayLoadBalancerConfiguration {

        @Bean
        ReactorLoadBalancer<ServiceInstance> userGrayLoadBalancer(
                LoadBalancerClientFactory loadBalancerClientFactory) {
            return new GrayLoadBalancer(
                    loadBalancerClientFactory.getLazyProvider("credit-user", ServiceInstanceListSupplier.class),
                    "credit-user");
        }
    }

}