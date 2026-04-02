package cn.edu.cqjtu.cs.credit.user.mapper;

import cn.edu.cqjtu.cs.credit.common.entity.po.SysPermission;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<String> selectPermissionsByUserId(Long id);
}