package net.maku.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 程序配置
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-01
 */

@Data
@TableName("t_app_config")
public class TAppConfigEntity {
	@TableId
	private Integer id;

	/**
	* 应用标识
	*/
	private String type;

	/**
	* 配置名称
	*/
	private String name;

	/**
	* 标识
	*/
	private String keyname;

	/**
	* 值
	*/
	private String value;

	/**
	* 状态
	*/
	private Object status;

	/**
	* 租户ID
	*/
	private Integer tenantId;

	/**
	* 版本号
	*/
	private Integer version;

	/**
	* 删除标识  0：正常   1：已删除
	*/
	private Integer deleted;

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