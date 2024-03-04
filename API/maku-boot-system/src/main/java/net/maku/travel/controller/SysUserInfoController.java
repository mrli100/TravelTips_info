package net.maku.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.service.SysUserService;
import net.maku.system.vo.SysUserBaseVO;
import net.maku.system.vo.SysUserVO;
import net.maku.travel.convert.SysUserInfoConvert;
import net.maku.travel.entity.SysUserInfoEntity;
import net.maku.travel.service.SysUserInfoService;
import net.maku.travel.query.SysUserInfoQuery;
import net.maku.travel.vo.SysUserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;

/**
 * 用户信息
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-04
 */
@RestController
@RequestMapping("travel/sysUserInfo")
@Tag(name = "用户信息")
@AllArgsConstructor
public class SysUserInfoController {
    private final SysUserInfoService sysUserInfoService;
    private final SysUserService sysUserService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:page')")
    public Result<PageResult<SysUserInfoVO>> page(@ParameterObject @Valid SysUserInfoQuery query) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            query.setCreator(user.getId().intValue());
        }
        PageResult<SysUserInfoVO> page = sysUserInfoService.page(query);

        return Result.ok(page);
    }

    @GetMapping("query")
    @Operation(summary = "查询列表对外提供API")
    public Result<PageResult<SysUserInfoVO>> query(@ParameterObject @Valid SysUserInfoQuery query) {
        PageResult<SysUserInfoVO> page = sysUserInfoService.page(query);

        return Result.ok(page);
    }


    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:info')")
    public Result<SysUserInfoVO> get(@PathVariable("id") Long id) {
        SysUserInfoEntity entity = sysUserInfoService.getById(id);

        return Result.ok(SysUserInfoConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:save')")
    public Result<String> save(@RequestBody SysUserInfoVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        sysUserInfoService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:update')")
    public Result<String> update(@RequestBody @Valid SysUserInfoVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
            //查询自己的用户信息
            LambdaQueryWrapper<SysUserInfoEntity> queryWrapper = new LambdaQueryWrapper<SysUserInfoEntity>();
            queryWrapper.eq(SysUserInfoEntity::getUserId, user.getId().intValue());
            SysUserInfoEntity one = sysUserInfoService.getOne(queryWrapper);
            vo.setId(one.getId());
        }

        vo.setUpdateTime(new Date());
        sysUserInfoService.update(vo);

        //用户名不为空，更新用户名信息
        if (StringUtils.isNotBlank(vo.getRealName()) || StringUtils.isNotBlank(vo.getAvatar())) {
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setRealName(vo.getRealName());
            sysUserEntity.setAvatar(vo.getAvatar());
            sysUserEntity.setId(user.getId());
            sysUserService.updateInfo(sysUserEntity);
        }
        return Result.ok("更新成功");
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('travel:sysUserInfo:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            sysUserInfoService.delete(idList);
        }

        return Result.ok();
    }
}
