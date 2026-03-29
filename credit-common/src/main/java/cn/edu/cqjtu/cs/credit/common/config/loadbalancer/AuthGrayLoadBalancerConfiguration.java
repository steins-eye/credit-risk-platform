package cn.edu.cqjtu.cs.credit.common.config.loadbalancer;

import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.context.annotation.Bean;


public class AuthGrayLoadBalancerConfiguration {

    @Bean
    ReactorLoadBalancer<ServiceInstance> authGrayLoadBalancer(
            LoadBalancerClientFactory loadBalancerClientFactory) {
        return new GrayLoadBalancer(
                loadBalancerClientFactory.getLazyProvider("credit-auth", ServiceInstanceListSupplier.class),
                "credit-auth");
    }
}