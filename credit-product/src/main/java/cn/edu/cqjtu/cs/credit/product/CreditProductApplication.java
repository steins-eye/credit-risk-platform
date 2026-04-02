package cn.edu.cqjtu.cs.credit.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.edu.cqjtu.cs.credit.product.feign")
@MapperScan("cn.edu.cqjtu.cs.credit.product.mapper")
public class CreditProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditProductApplication.class, args);
    }

}