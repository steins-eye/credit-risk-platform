package cn.edu.cqjtu.cs.credit.user.controller;

import cn.edu.cqjtu.cs.credit.common.entity.Result;
import cn.edu.cqjtu.cs.credit.common.entity.po.SysPermission;
import cn.edu.cqjtu.cs.credit.user.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @PostMapping
    @PreAuthorize("hasAuthority('sys:permission:create')")
    public Result<SysPermission> create(@RequestBody SysPermission sysPermission) {
        sysPermissionService.save(sysPermission);
        return Result.success(sysPermission);
    }

    @PutMapping("/{permissionId}")
    public Result<SysPermission> update(@PathVariable Long permissionId, @RequestBody SysPermission sysPermission) {
        sysPermission.setId(permissionId);
        sysPermissionService.updateById(sysPermission);
        return Result.success(sysPermission);
    }

    @DeleteMapping("/{permissionId}")
    public Result<Void> delete(@PathVariable Long permissionId) {
        sysPermissionService.removeById(permissionId);
        return Result.success();
    }

    @GetMapping("/{permissionId}")
    @PreAuthorize("hasAuthority('sys:permission:read')")
    public Result<SysPermission> getById(@PathVariable Long permissionId) {
        SysPermission sysPermission = sysPermissionService.getById(permissionId);
        return Result.success(sysPermission);
    }

    @GetMapping
    public Result<List<SysPermission>> list() {
        List<SysPermission> list = sysPermissionService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result<Page<SysPermission>> page(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysPermission> page = sysPermissionService.page(new Page<>(pageNum, pageSize));
        return Result.success(page);
    }
}