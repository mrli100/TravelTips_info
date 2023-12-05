package net.maku.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.system.convert.SysUserInfoConvert;
import net.maku.system.entity.SysUserInfoEntity;
import net.maku.system.query.SysUserInfoQuery;
import net.maku.system.service.SysUserInfoService;
import net.maku.system.vo.SysUserInfoVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* 用户信息表
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-05
*/
@RestController
@RequestMapping("maku/sys_user_info")
@Tag(name="用户信息表")
@AllArgsConstructor
public class SysUserInfoController {
    private final SysUserInfoService sysUserInfoService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('maku:sys_user_info:page')")
    public Result<PageResult<SysUserInfoVO>> page(@ParameterObject @Valid SysUserInfoQuery query){
        PageResult<SysUserInfoVO> page = sysUserInfoService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('maku:sys_user_info:info')")
    public Result<SysUserInfoVO> get(@PathVariable("id") Long id){
        SysUserInfoEntity entity = sysUserInfoService.getById(id);

        return Result.ok(SysUserInfoConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('maku:sys_user_info:save')")
    public Result<String> save(@RequestBody SysUserInfoVO vo){
        sysUserInfoService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('maku:sys_user_info:update')")
    public Result<String> update(@RequestBody @Valid SysUserInfoVO vo){
        sysUserInfoService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('maku:sys_user_info:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        sysUserInfoService.delete(idList);

        return Result.ok();
    }
}
