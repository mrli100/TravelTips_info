package net.maku.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 用户信息
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-10
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
	* 删除标识  0：正常   1：已删除
	*/
	private Integer deleted;

	/**
	* 身份证
	*/
	private String idcard;

	/**
	* 创建者
	*/
	private Integer creator;

	/**
	* 创建时间
	*/
	private Date createTime;

	/**
	* 更新者
	*/
	private Integer updater;

	/**
	* 更新时间
	*/
	private Date updateTime;

}