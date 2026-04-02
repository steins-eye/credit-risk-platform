package cn.edu.cqjtu.cs.credit.user.controller;

import cn.edu.cqjtu.cs.credit.common.entity.Result;
import cn.edu.cqjtu.cs.credit.common.entity.po.SysRole;
import cn.edu.cqjtu.cs.credit.user.service.SysRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping
    public Result<SysRole> create(@RequestBody SysRole sysRole) {
        sysRoleService.save(sysRole);
        return Result.success(sysRole);
    }

    @PutMapping("/{roleId}")
    public Result<SysRole> update(@PathVariable Long roleId, @RequestBody SysRole sysRole) {
        sysRole.setId(roleId);
        sysRoleService.updateById(sysRole);
        return Result.success(sysRole);
    }

    @DeleteMapping("/{roleId}")
    public Result<Void> delete(@PathVariable Long roleId) {
        sysRoleService.removeById(roleId);
        return Result.success();
    }

    @GetMapping("/{roleId}")
    public Result<SysRole> getById(@PathVariable Long roleId) {
        SysRole sysRole = sysRoleService.getById(roleId);
        return Result.success(sysRole);
    }

    @GetMapping
    public Result<List<SysRole>> list() {
        List<SysRole> list = sysRoleService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<Page<SysRole>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysRole> page = sysRoleService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }
}