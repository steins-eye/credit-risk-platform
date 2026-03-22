package cn.edu.cqjtu.cs.credit.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_permission")
public class SysPermission {
    @TableId
    private Long permissionId;
    private Long parentId;
    private String name;
    private String perCode;
    private Integer type;
    private String path;
    private LocalDateTime createTime;
}