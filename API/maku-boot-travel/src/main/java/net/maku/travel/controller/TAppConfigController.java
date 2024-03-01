package net.maku.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.travel.convert.TAppConfigConvert;
import net.maku.travel.entity.TAppConfigEntity;
import net.maku.travel.service.TAppConfigService;
import net.maku.travel.query.TAppConfigQuery;
import net.maku.travel.vo.TAppConfigVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 程序配置
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-01
 */
@RestController
@RequestMapping("travel/t_app_config")
@Tag(name = "程序配置")
@AllArgsConstructor
public class TAppConfigController {
    private final TAppConfigService tAppConfigService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('travel:t_app_config:page')")
    public Result<PageResult<TAppConfigVO>> page(@ParameterObject @Valid TAppConfigQuery query) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            query.setCreator(user.getId().intValue());
        }
        PageResult<TAppConfigVO> page = tAppConfigService.page(query);

        return Result.ok(page);
    }

    @GetMapping("query")
    @Operation(summary = "查询列表对外提供API")
    public Result<PageResult<TAppConfigVO>> query(@ParameterObject @Valid TAppConfigQuery query) {
        PageResult<TAppConfigVO> page = tAppConfigService.page(query);

        return Result.ok(page);
    }

    @GetMapping("get")
    @Operation(summary = "查询所有配置")
    public Result<Map> getAll() {
        Map rtnMap = new HashMap();
        List<TAppConfigEntity> list = tAppConfigService.list();
        list.stream().forEach(item -> {
            rtnMap.put(item.getKeyname(), item.getValue());
        });
        return Result.ok(rtnMap);
    }


    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('travel:t_app_config:info')")
    public Result<TAppConfigVO> get(@PathVariable("id") Long id) {
        TAppConfigEntity entity = tAppConfigService.getById(id);

        return Result.ok(TAppConfigConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('travel:t_app_config:save')")
    public Result<String> save(@RequestBody TAppConfigVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tAppConfigService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('travel:t_app_config:update')")
    public Result<String> update(@RequestBody @Valid TAppConfigVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tAppConfigService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('travel:t_app_config:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            tAppConfigService.delete(idList);
        }

        return Result.ok();
    }
}
