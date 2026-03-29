package cn.edu.cqjtu.cs.credit.gateway.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class GrayLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
    private final String serviceId;

    public GrayLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.serviceId = serviceId;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        var serviceInstanceListSupplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return serviceInstanceListSupplier.get(request).next()
                .map(instances -> processInstanceResponse(instances, request));
    }

    private Response<ServiceInstance> processInstanceResponse(List<ServiceInstance> instances, Request request) {
        if (CollectionUtils.isEmpty(instances)) {
            return new EmptyResponse();
        }

        // 1. 提取请求头中的 gray-tag
        String grayTag = null;
        if (request.getContext() instanceof RequestDataContext) {
            HttpHeaders headers = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders();
            grayTag = headers.getFirst("gray-tag");
        }

        final String finalGrayTag = grayTag;

        // 2. 核心过滤逻辑
        if (StringUtils.hasText(finalGrayTag)) {
            // 【场景A】携带灰度头：优先转发到匹配的本地实例
            List<ServiceInstance> grayInstances = instances.stream()
                    .filter(inst -> finalGrayTag.equals(inst.getMetadata().get("gray-tag")))
                    .collect(Collectors.toList());
            
            if (!grayInstances.isEmpty()) {
                return new DefaultResponse(grayInstances.get(0));
            }
            // 如果没找到匹配的本地实例，退化回第一个可用实例（防止请求直接挂掉）
            return new DefaultResponse(instances.get(0));
        } else {
            // 【场景B】普通请求（无头）：剔除所有带 gray-tag 的本地实例
            List<ServiceInstance> serverInstances = instances.stream()
                    .filter(inst -> !inst.getMetadata().containsKey("gray-tag") 
                                 || !StringUtils.hasText(inst.getMetadata().get("gray-tag")))
                    .collect(Collectors.toList());

            // 如果只剩下本地实例了（服务器全挂），则返回全部；否则只返回服务器实例
            List<ServiceInstance> finalInstances = serverInstances.isEmpty() ? instances : serverInstances;
            return new DefaultResponse(finalInstances.get(0));
        }
    }
}