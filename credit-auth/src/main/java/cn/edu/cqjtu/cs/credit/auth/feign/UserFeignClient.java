package cn.edu.cqjtu.cs.credit.auth.feign;

import cn.edu.cqjtu.cs.credit.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "credit-user")
public interface UserFeignClient {

    @GetMapping("/api/user/username/{username}")
    Result<Map<String, Object>> getByUsername(@PathVariable String username);

}