package cn.edu.cqjtu.cs.credit.user.service.impl;

import cn.edu.cqjtu.cs.credit.common.entity.po.SysUser;
import cn.edu.cqjtu.cs.credit.user.mapper.SysUserMapper;
import cn.edu.cqjtu.cs.credit.user.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}