package cn.edu.cqjtu.cs.credit.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.cqjtu.cs.credit.user.mapper")
public class CreditUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditUserApplication.class, args);
    }
}