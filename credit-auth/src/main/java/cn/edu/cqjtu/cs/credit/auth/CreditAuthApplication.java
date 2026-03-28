package cn.edu.cqjtu.cs.credit.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "cn.edu.cqjtu.cs.credit.auth",
        "cn.edu.cqjtu.cs.credit.common",
})
@EnableDiscoveryClient
@EnableFeignClients
public class CreditAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditAuthApplication.class, args);
    }

}