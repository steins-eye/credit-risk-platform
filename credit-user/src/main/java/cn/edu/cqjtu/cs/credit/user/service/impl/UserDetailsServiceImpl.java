package cn.edu.cqjtu.cs.credit.user.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cn.edu.cqjtu.cs.credit.common.entity.login.LoginUser;
import cn.edu.cqjtu.cs.credit.common.entity.po.SysUser;
import cn.edu.cqjtu.cs.credit.user.mapper.SysPermissionMapper;
import cn.edu.cqjtu.cs.credit.user.mapper.SysUserMapper;

import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 查询数据库获取用户信息
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (Objects.isNull(user)) throw new UsernameNotFoundException("用户不存在");

        // 2. 查询权限标识符 (根据用户ID关联角色和权限表联查)
        // 建议在 Mapper 中写一个自定义 SQL
        // 查询权限标识符 (根据用户ID关联角色和权限表联查)
        List<String> perms = permissionMapper.selectPermissionsByUserId(user.getId());

        // 3. 封装返回
        return new LoginUser(user, perms);
    }

}
