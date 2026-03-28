package cn.edu.cqjtu.cs.credit.user.controller;

import cn.edu.cqjtu.cs.credit.common.entity.Result;
import cn.edu.cqjtu.cs.credit.user.entity.SysUser;
import cn.edu.cqjtu.cs.credit.user.service.SysUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping
    public Result<SysUser> create(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return Result.success(sysUser);
    }

    @PutMapping("/{userId}")
    public Result<SysUser> update(@PathVariable Long userId, @RequestBody SysUser sysUser) {
        sysUser.setUserId(userId);
        sysUserService.updateById(sysUser);
        return Result.success(sysUser);
    }

    @DeleteMapping("/{userId}")
    public Result<Void> delete(@PathVariable Long userId) {
        sysUserService.removeById(userId);
        return Result.success();
    }

    @GetMapping("/{userId}")
    public Result<SysUser> getById(@PathVariable Long userId) {
        SysUser sysUser = sysUserService.getById(userId);
        return Result.success(sysUser);
    }

    @GetMapping
    public Result<List<SysUser>> list() {
        List<SysUser> list = sysUserService.list();
        return Result.success(list);
    }

    @GetMapping("/username/{username}")
    public Result<SysUser> getByUsername(@PathVariable String username) {
        SysUser sysUser = sysUserService.lambdaQuery().eq(SysUser::getUsername, username).one();
        return Result.success(sysUser);
    }

    @GetMapping("/page")
    public Result<Page<SysUser>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysUser> page = sysUserService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }
}