package net.maku.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 用户信息
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-04
 */

@Data
@TableName("sys_user_info")
public class SysUserInfoEntity {
	/**
	* id
	*/
	@TableId
	private Integer id;

	/**
	* 用户id
	*/
	private Long userId;

	/**
	* 签名
	*/
	private String signature;

	/**
	* 标签
	*/
	private String tags;

	/**
	* 租户ID
	*/
	private Integer tenantId;

	/**
	* 扩展配置
	*/
	private String extendedconfig;

	/**
	* 版本号
	*/
	private Integer version;

	/**
	* 出生年月
	*/
	private Date birthday;

	/**
	* 职业
	*/
	private String career;

	/**
	* 删除标识  0：正常   1：已删除
	*/
	private Integer deleted;

	/**
	* 微信openid
	*/
	private String weixinOpenid;

	/**
	* 身份证
	*/
	private String idcard;

	/**
	* 登录地址
	*/
	private String addressIp;

	/**
	* 创建者
	*/
	private Integer creator;

	/**
	* 纬度
	*/
	private String addressLat;

	/**
	* 创建时间
	*/
	private Date createTime;

	/**
	* 经度
	*/
	private String addressLng;

	/**
	* 年龄
	*/
	private String age;

	/**
	* 更新者
	*/
	private Integer updater;

	/**
	* 更新时间
	*/
	private Date updateTime;

}