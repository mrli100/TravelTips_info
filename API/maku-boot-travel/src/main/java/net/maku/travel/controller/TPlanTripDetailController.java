package net.maku.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


import net.maku.framework.common.constant.Constant;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.travel.convert.TPlanTripDetailConvert;
import net.maku.travel.entity.TPlanTripDetailEntity;
import net.maku.travel.service.TPlanTripDetailService;
import net.maku.travel.query.TPlanTripDetailQuery;
import net.maku.travel.vo.TPlanTripDetailVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

/**
 * 计划明细
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-01-28
 */
@RestController
@RequestMapping("travel/t_plan_trip_detail")
@Tag(name = "计划明细")
@AllArgsConstructor
public class TPlanTripDetailController {
    private final TPlanTripDetailService tPlanTripDetailService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('travel:t_plan_trip_detail:page')")
    public Result<PageResult<TPlanTripDetailVO>> page(@ParameterObject @Valid TPlanTripDetailQuery query) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            query.setCreator(user.getId().intValue());
        }
        PageResult<TPlanTripDetailVO> page = tPlanTripDetailService.page(query);

        return Result.ok(page);
    }

    @GetMapping("query")
    @Operation(summary = "查询列表对外提供API")
    public Result<PageResult<TPlanTripDetailVO>> query(@ParameterObject @Valid TPlanTripDetailQuery query) {
        PageResult<TPlanTripDetailVO> page = tPlanTripDetailService.page(query);

        return Result.ok(page);
    }


    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('travel:t_plan_trip_detail:info')")
    public Result<TPlanTripDetailVO> get(@PathVariable("id") Long id) {
        TPlanTripDetailEntity entity = tPlanTripDetailService.getById(id);

        return Result.ok(TPlanTripDetailConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('travel:t_plan_trip_detail:save')")
    public Result<String> save(@RequestBody TPlanTripDetailVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tPlanTripDetailService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('travel:t_plan_trip_detail:update')")
    public Result<String> update(@RequestBody @Valid TPlanTripDetailVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tPlanTripDetailService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('travel:t_plan_trip_detail:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            tPlanTripDetailService.delete(idList);
        }

        return Result.ok();
    }
}