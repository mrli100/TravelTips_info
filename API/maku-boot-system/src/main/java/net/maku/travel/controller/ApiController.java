package net.maku.travel.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.operatelog.annotations.OperateLog;
import net.maku.framework.operatelog.enums.OperateTypeEnum;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.system.convert.SysUserConvert;
import net.maku.system.entity.SysLogLoginEntity;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.service.SysLogLoginService;
import net.maku.system.service.SysUserService;
import net.maku.system.vo.SysUserVO;
import net.maku.travel.entity.SysUserInfoEntity;
import net.maku.travel.query.TPlanTripMainQuery;
import net.maku.travel.service.SysUserInfoService;
import net.maku.travel.service.TPlanTripMainService;
import net.maku.travel.vo.TPlanTripMainVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("travel")
@AllArgsConstructor
@Tag(name = "用户信息API接口集合")
public class ApiController {
    private final SysLogLoginService sysLogLoginService;
    private final SysUserInfoService sysUserInfoService;
    private final SysUserService sysUserService;
    private final TPlanTripMainService tPlanTripMainService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user/info")
    @Operation(summary = "查询登录用户信息")
    public Result<Map> info() {
        SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());
        SysUserEntity byId = sysUserService.getById(user.getId());
        Map map = new HashMap<>();
        map.put("userId", byId.getId());
        map.put("username", byId.getUsername());
        map.put("realName", byId.getRealName());
        map.put("email", byId.getEmail());
        map.put("mobile", byId.getMobile());
        map.put("gender", byId.getGender());
        map.put("avatar", byId.getAvatar());
        map.put("orgId", byId.getOrgId());
        //查询用户登录最后IP地址信息
        LambdaQueryWrapper<SysLogLoginEntity> queryLogin = new LambdaQueryWrapper<SysLogLoginEntity>();
        queryLogin.eq(SysLogLoginEntity::getUsername, byId.getUsername());
        queryLogin.orderByDesc(SysLogLoginEntity::getCreateTime);
        List<SysLogLoginEntity> list = sysLogLoginService.list(queryLogin);
        if (list.size() > 0) {
            String address = list.get(0).getAddress();
            String[] addressArr = address.split(" ");
            map.put("province", addressArr.length == 4 ? addressArr[2] : addressArr[addressArr.length - 1]);
        } else {
            map.put("province", "未知");
        }
        //查询用户信息
        LambdaQueryWrapper<SysUserInfoEntity> query = new LambdaQueryWrapper<SysUserInfoEntity>();
        query.eq(SysUserInfoEntity::getUserId, byId.getId());
        SysUserInfoEntity one = sysUserInfoService.getOne(query);
        if (null != one) {
            map.put("signature", one.getSignature());
            map.put("tags", one.getTags().split(","));
            map.put("tags", one.getTags().split(","));
            map.put("birthday", one.getBirthday());
            map.put("extendedconfig", one.getExtendedconfig());
            map.put("idcard", one.getIdcard());
            //查询职业
            map.put("career", one.getCareer());
            map.put("fans", 0);
            map.put("follow", 0);

        } else {
            map.put("signature", "该用户太懒了，没有什么介绍。");
            map.put("tags", "");
            map.put("birthday", "");
            map.put("extendedconfig", "{}");
            map.put("idcard", "");
            map.put("career", "");
            map.put("fans", 0);
            map.put("follow", 0);
        }
        return Result.ok(map);
    }

    @PostMapping("/user/register")
    @Operation(summary = "用户注册")
    @OperateLog(type = OperateTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> save(@RequestBody @Valid SysUserVO vo) {
        // 新增密码不能为空
        if (StrUtil.isBlank(vo.getPassword())) {
            return Result.error("密码不能为空");
        }

        // 密码加密
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        // 保存
        sysUserService.save(vo);

        return Result.ok();
    }

    @GetMapping("/message/user_message_count")
    @Operation(summary = "查询用户消息数量")
    public Result<Map> travelInfo() {
        SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());
        SysUserEntity byId = sysUserService.getById(user.getId());
        Map map = new HashMap<>();
        map.put("data", "0");
        return Result.ok(map);
    }


    @GetMapping("/travelInfo")
    @Operation(summary = "查询个人旅游信息资料")
    public Result<Map> travelUserInfo() {
        UserDetail user = SecurityUser.getUser();
        Map map = new HashMap<>();

        //查詢草稿箱的内容
        TPlanTripMainQuery query = new TPlanTripMainQuery();
        query.setPage(1);
        query.setLimit(10);
        PageResult<TPlanTripMainVO> page = tPlanTripMainService.page(query);
        map.put("drafts", page.getTotal());
        return Result.ok(map);
    }
}
