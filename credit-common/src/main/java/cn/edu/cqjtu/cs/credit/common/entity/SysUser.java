package cn.edu.cqjtu.cs.credit.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private Integer status;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createTime;
}