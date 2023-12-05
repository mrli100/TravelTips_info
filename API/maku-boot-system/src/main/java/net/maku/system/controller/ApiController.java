package net.maku.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.user.SecurityUser;
import net.maku.system.convert.SysUserConvert;
import net.maku.system.entity.SysUserInfoEntity;
import net.maku.system.service.SysUserInfoService;
import net.maku.system.vo.SysUserVO;
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

    @GetMapping("/user/info")
    @Operation(summary = "登录用户")
    public Result<Map> info() {
        SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());
        Map map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("username", user.getUsername());
        map.put("realName", user.getRealName());
        map.put("email", user.getEmail());
        map.put("mobile", user.getMobile());
        map.put("gender", user.getGender());
        map.put("avatar", user.getAvatar());

        //查询用户信息
        LambdaQueryWrapper<SysUserInfoEntity> query = new LambdaQueryWrapper<SysUserInfoEntity>();
        query.eq(SysUserInfoEntity::getUserId, user.getId());
        SysUserInfoEntity one = sysUserInfoService.getOne(query);
        if (null != one) {
            map.put("signature", one.getSignature());
            map.put("tags", one.getTags().split(","));
        } else {
            map.put("signature", "该用户太懒了，没有什么介绍。");
            map.put("tags", "");
        }
        return Result.ok(map);
    }

}
