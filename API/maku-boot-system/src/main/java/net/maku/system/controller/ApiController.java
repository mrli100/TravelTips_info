package net.maku.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.HttpContextUtils;
import net.maku.framework.common.utils.IpUtils;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.user.SecurityUser;
import net.maku.system.convert.SysUserConvert;
import net.maku.system.entity.SysLogLoginEntity;
import net.maku.system.entity.SysUserEntity;
import net.maku.system.service.SysLogLoginService;
import net.maku.system.service.SysUserService;
import net.maku.system.vo.SysUserVO;
import net.maku.travel.entity.SysUserInfoEntity;
import net.maku.travel.service.SysUserInfoService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/message/user_message_count")
    @Operation(summary = "查询用户消息数量")
    public Result<Map> travelInfo() {
        SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());
        SysUserEntity byId = sysUserService.getById(user.getId());
        Map map = new HashMap<>();
        map.put("data", "0");
        return Result.ok(map);
    }
}
