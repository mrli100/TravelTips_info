package net.maku.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.travel.convert.TPlanTripMainConvert;
import net.maku.travel.entity.TPlanTripMainEntity;
import net.maku.travel.query.TPlanTripMainQuery;
import net.maku.travel.service.TPlanTripMainService;
import net.maku.travel.vo.TPlanTripMainVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 计划路线
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-10
 */
@RestController
@RequestMapping("travel/planMain")
@Tag(name = "计划路线")
@AllArgsConstructor
public class TPlanTripMainController {
    private final TPlanTripMainService tPlanTripMainService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('travel:planMain:page')")
    public Result<PageResult<TPlanTripMainVO>> page(@ParameterObject @Valid TPlanTripMainQuery query) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            query.setCreator(user.getId().intValue());
        }
        PageResult<TPlanTripMainVO> page = tPlanTripMainService.page(query);
        return Result.ok(page);
    }

    @GetMapping("query")
    @Operation(summary = "查询列表对外提供API")
    public Result<PageResult<TPlanTripMainVO>> query(@ParameterObject @Valid TPlanTripMainQuery query) {
        PageResult<TPlanTripMainVO> page = tPlanTripMainService.page(query);
        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('travel:planMain:info')")
    public Result<TPlanTripMainVO> get(@PathVariable("id") Long id) {
        TPlanTripMainEntity entity = tPlanTripMainService.getById(id);

        return Result.ok(TPlanTripMainConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('travel:planMain:save')")
    public Result<String> save(@RequestBody TPlanTripMainVO vo) {
        vo.setState(0);
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tPlanTripMainService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('travel:planMain:update')")
    public Result<String> update(@RequestBody @Valid TPlanTripMainVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tPlanTripMainService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('travel:planMain:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            tPlanTripMainService.delete(idList);
        }

        return Result.ok();
    }
}