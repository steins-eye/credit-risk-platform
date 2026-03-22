package cn.edu.cqjtu.cs.credit.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_role")
public class SysRole {
    @TableId
    private Long roleId;
    private String roleName;
    private String roleKey;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;
}