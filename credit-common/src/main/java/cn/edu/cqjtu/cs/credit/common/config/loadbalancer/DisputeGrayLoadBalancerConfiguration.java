package cn.edu.cqjtu.cs.credit.common.config.loadbalancer;

import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.client.ServiceInstance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DisputeGrayLoadBalancerConfiguration {

    @Bean
    ReactorLoadBalancer<ServiceInstance> disputeGrayLoadBalancer(
            LoadBalancerClientFactory loadBalancerClientFactory) {
        return new GrayLoadBalancer(
                loadBalancerClientFactory.getLazyProvider("credit-dispute", ServiceInstanceListSupplier.class),
                "credit-dispute");
    }
}