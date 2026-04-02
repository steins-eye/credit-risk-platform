package cn.edu.cqjtu.cs.credit.user.entity.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 登录请求参数对象
 */
@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名 / 手机号 / 邮箱
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}