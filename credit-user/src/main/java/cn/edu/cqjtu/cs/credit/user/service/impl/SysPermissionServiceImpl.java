package cn.edu.cqjtu.cs.credit.user.service.impl;

import cn.edu.cqjtu.cs.credit.common.entity.po.SysPermission;
import cn.edu.cqjtu.cs.credit.user.mapper.SysPermissionMapper;
import cn.edu.cqjtu.cs.credit.user.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
}