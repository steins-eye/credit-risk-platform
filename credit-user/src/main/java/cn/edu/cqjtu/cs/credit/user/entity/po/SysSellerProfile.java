package cn.edu.cqjtu.cs.credit.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.edu.cqjtu.cs.credit.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_seller_profile")
public class SysSellerProfile extends BaseEntity {
    @TableId
    private Long id;
    private Long userId;
    private String realName;
    private String idCard;
    private String businessLicense;
    private Integer initialCreditScore;
}