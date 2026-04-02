package cn.edu.cqjtu.cs.credit.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.edu.cqjtu.cs.credit.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_buyer_profile")
public class SysBuyerProfile extends BaseEntity {
    @TableId
    private Long id;
    private Long userId;
    private String realName;
    private String idCard;
    private Integer initialCreditScore;
    private LocalDateTime createTime;
}