package cn.edu.cqjtu.cs.credit.common.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.edu.cqjtu.cs.credit.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限资源表实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
public class SysPermission extends BaseEntity {
    @TableId
    private Long id;
    private Long parentId;
    private String name;
    private String perCode;
    private Integer type;
    private String path;
}