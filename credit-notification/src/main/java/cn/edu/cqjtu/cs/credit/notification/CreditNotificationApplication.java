package cn.edu.cqjtu.cs.credit.notification;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.edu.cqjtu.cs.credit.notification.feign")
@MapperScan("cn.edu.cqjtu.cs.credit.notification.mapper")
public class CreditNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditNotificationApplication.class, args);
    }

}