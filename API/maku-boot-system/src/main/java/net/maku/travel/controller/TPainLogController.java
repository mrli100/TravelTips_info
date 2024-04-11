package net.maku.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.constant.Constant;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.travel.convert.TPainLogConvert;
import net.maku.travel.entity.TPainLogEntity;
import net.maku.travel.service.TPainLogService;
import net.maku.travel.query.TPainLogQuery;
import net.maku.travel.vo.TPainLogVO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

/**
 * 痛苦日志记录
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-04-11
 */
@RestController
@RequestMapping("travel/t_pain_log")
@Tag(name = "痛苦日志记录")
@AllArgsConstructor
public class TPainLogController {
    private final TPainLogService tPainLogService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('travel:t_pain_log:page')")
    public Result<PageResult<TPainLogVO>> page(@ParameterObject @Valid TPainLogQuery query) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            //query.setCreator(user.getId().intValue());
        }
        PageResult<TPainLogVO> page = tPainLogService.page(query);

        return Result.ok(page);
    }

    @GetMapping("query")
    @Operation(summary = "查询列表对外提供API")
    public Result<PageResult<TPainLogVO>> query(@ParameterObject @Valid TPainLogQuery query) {
        PageResult<TPainLogVO> page = tPainLogService.page(query);

        return Result.ok(page);
    }


    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('travel:t_pain_log:info')")
    public Result<TPainLogVO> get(@PathVariable("id") Long id) {
        TPainLogEntity entity = tPainLogService.getById(id);

        return Result.ok(TPainLogConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('travel:t_pain_log:save')")
    public Result<String> save(@RequestBody TPainLogVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tPainLogService.save(vo);

        return Result.ok();
    }

    @PostMapping("add")
    @Operation(summary = "保存")
    public Result<String> saveAdd(@RequestBody TPainLogVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tPainLogService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('travel:t_pain_log:update')")
    public Result<String> update(@RequestBody @Valid TPainLogVO vo) {
        UserDetail user = SecurityUser.getUser();
        if (!user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            vo.setCreator(user.getId().intValue());
        }
        tPainLogService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('travel:t_pain_log:delete')")
    public Result<String> delete(@RequestBody List<Long> idList) {
        UserDetail user = SecurityUser.getUser();
        if (user.getSuperAdmin().equals(Constant.SUPER_ADMIN)) {
            tPainLogService.delete(idList);
        }

        return Result.ok();
    }
}
