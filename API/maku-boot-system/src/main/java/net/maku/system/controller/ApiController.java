package net.maku.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.user.SecurityUser;
import net.maku.system.convert.SysUserConvert;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.service.SysUserService;
import net.maku.system.vo.SysUserVO;
import net.maku.travel.entity.SysUserInfoEntity;
import net.maku.travel.service.SysUserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1")
@AllArgsConstructor
@Tag(name = "API接口集合")
public class ApiController {
    private final SysUserInfoService sysUserInfoService;
    private final SysUserService sysUserService;

    @GetMapping("/user/info")
    @Operation(summary = "登录用户")
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
        } else {
            map.put("signature", "该用户太懒了，没有什么介绍。");
            map.put("tags", "");
            map.put("birthday", "");
            map.put("extendedconfig", "{}");
            map.put("idcard", "");
        }
        return Result.ok(map);
    }


}
