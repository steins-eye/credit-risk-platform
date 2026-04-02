package cn.edu.cqjtu.cs.credit.common.util;

import org.springframework.security.core.context.SecurityContextHolder;

import cn.edu.cqjtu.cs.credit.common.entity.login.LoginUser;

public class SecurityUtil {

    /**
     * 获取当前登录用户对象
     */
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息异常");
        }
    }

    /**
     * 只获取用户ID (最常用)
     */
    public static Long getUserId() {
        return getLoginUser().getSysUser().getId();
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        return getLoginUser().getUsername();
    }
}