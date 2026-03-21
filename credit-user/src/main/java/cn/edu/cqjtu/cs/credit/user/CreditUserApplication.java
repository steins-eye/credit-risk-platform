package cn.edu.cqjtu.cs.credit.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CreditUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditUserApplication.class, args);
        System.out.println("🚀 信用风险管理 - 用户服务启动成功！");
    }
}