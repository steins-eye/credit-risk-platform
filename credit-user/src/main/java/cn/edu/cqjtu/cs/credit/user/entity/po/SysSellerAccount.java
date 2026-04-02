package cn.edu.cqjtu.cs.credit.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.edu.cqjtu.cs.credit.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_seller_account")
public class SysSellerAccount extends BaseEntity {
    @TableId
    private Long id;
    private Long userId;
    private BigDecimal balance;
}