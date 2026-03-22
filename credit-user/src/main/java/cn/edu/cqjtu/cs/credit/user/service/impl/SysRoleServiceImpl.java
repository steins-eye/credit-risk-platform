package cn.edu.cqjtu.cs.credit.user.service.impl;

import cn.edu.cqjtu.cs.credit.user.entity.SysRole;
import cn.edu.cqjtu.cs.credit.user.mapper.SysRoleMapper;
import cn.edu.cqjtu.cs.credit.user.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}