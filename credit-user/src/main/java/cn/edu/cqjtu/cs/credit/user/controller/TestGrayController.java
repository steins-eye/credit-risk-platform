package cn.edu.cqjtu.cs.credit.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-gray")
public class TestGrayController {
    @GetMapping("/test")
    public String test() {
        return "test-gray";
    }
}
