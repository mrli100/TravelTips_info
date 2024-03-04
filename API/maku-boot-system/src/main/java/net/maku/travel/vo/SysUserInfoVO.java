package net.maku.travel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

import net.maku.framework.common.utils.DateUtils;

import java.util.Date;

/**
 * 用户信息
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-04
 */
@Data
@Schema(description = "用户信息")
public class SysUserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "签名")
    private String signature;

    @Schema(description = "标签")
    private String tags;

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "扩展配置")
    private String extendedconfig;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "出生年月")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date birthday;

    @Schema(description = "职业")
    private String career;

    @Schema(description = "删除标识  0：正常   1：已删除")
    private Integer deleted;

    @Schema(description = "微信openid")
    private String weixinOpenid;

    @Schema(description = "身份证")
    private String idcard;

    @Schema(description = "登录地址")
    private String addressIp;

    @Schema(description = "创建者")
    private Integer creator;

    @Schema(description = "纬度")
    private String addressLat;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date createTime;

    @Schema(description = "经度")
    private String addressLng;

    @Schema(description = "年龄")
    private String age;

    @Schema(description = "更新者")
    private Integer updater;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date updateTime;

    /**
     * 额外补录信息
     */
    @Schema(description = "用户昵称")
    private String realName;

    @Schema(description = "头像")
    private String avatar;
}
