package cn.edu.cqjtu.cs.credit.user.entity.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 验证码请求参数对象
 */
@Data
public class ValidateDTO implements Serializable {

    /**
     * 验证码
     */
    private String token;

}
