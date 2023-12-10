package net.maku.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.travel.convert.SysUserInfoConvert;
import net.maku.travel.entity.SysUserInfoEntity;
import net.maku.travel.service.SysUserInfoService;
import net.maku.travel.query.SysUserInfoQuery;
import net.maku.travel.vo.SysUserInfoVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 用户信息
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-10
*/
@RestController
@RequestMapping("travel/sysUserInfo")
@Tag(name="用户信息")
@AllArgsConstructor
public class SysUserInfoController {
    private final SysUserInfoService sysUserInfoService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:page')")
    public Result<PageResult<SysUserInfoVO>> page(@ParameterObject @Valid SysUserInfoQuery query){
        PageResult<SysUserInfoVO> page = sysUserInfoService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:info')")
    public Result<SysUserInfoVO> get(@PathVariable("id") Long id){
        SysUserInfoEntity entity = sysUserInfoService.getById(id);

        return Result.ok(SysUserInfoConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:save')")
    public Result<String> save(@RequestBody SysUserInfoVO vo){
        sysUserInfoService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:update')")
    public Result<String> update(@RequestBody @Valid SysUserInfoVO vo){
        sysUserInfoService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        sysUserInfoService.delete(idList);

        return Result.ok();
    }
}