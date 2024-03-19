package net.maku.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.travel.convert.TAppSnsCircleConvert;
import net.maku.travel.entity.TAppSnsCircleEntity;
import net.maku.travel.service.TAppSnsCircleService;
import net.maku.travel.query.TAppSnsCircleQuery;
import net.maku.travel.vo.TAppSnsCircleVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
* 圈子表
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-12
*/
@RestController
@RequestMapping("travel/appSnsCircle")
@Tag(name="圈子表")
@AllArgsConstructor
public class TAppSnsCircleController {
    private final TAppSnsCircleService tAppSnsCircleService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('travel:appSnsCircle:page')")
    public Result<PageResult<TAppSnsCircleVO>> page(@ParameterObject @Valid TAppSnsCircleQuery query){
    UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            query.setCreator(user.getId().intValue());
        }
        PageResult<TAppSnsCircleVO> page = tAppSnsCircleService.page(query);

        return Result.ok(page);
    }

    @GetMapping("query")
    @Operation(summary = "查询列表对外提供API")
    public Result<PageResult<TAppSnsCircleVO>> query(@ParameterObject @Valid TAppSnsCircleQuery query){
        PageResult<TAppSnsCircleVO> page = tAppSnsCircleService.page(query);

        return Result.ok(page);
    }


    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('travel:appSnsCircle:info')")
    public Result<TAppSnsCircleVO> get(@PathVariable("id") Long id){
        TAppSnsCircleEntity entity = tAppSnsCircleService.getById(id);

        return Result.ok(TAppSnsCircleConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('travel:appSnsCircle:save')")
    public Result<String> save(@RequestBody TAppSnsCircleVO vo){
        UserDetail user = SecurityUser.getUser();
            if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tAppSnsCircleService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('travel:appSnsCircle:update')")
    public Result<String> update(@RequestBody @Valid TAppSnsCircleVO vo){
        UserDetail user = SecurityUser.getUser();
            if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tAppSnsCircleService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('travel:appSnsCircle:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        UserDetail user = SecurityUser.getUser();
            if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            tAppSnsCircleService.delete(idList);
        }

        return Result.ok();
    }
}
