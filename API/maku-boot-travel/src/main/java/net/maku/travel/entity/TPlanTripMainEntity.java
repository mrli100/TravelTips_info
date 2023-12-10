package net.maku.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 计划路线
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-10
 */

@Data
@TableName("t_plan_trip_main")
public class TPlanTripMainEntity {
	/**
	* 主键id
	*/
	@TableId
	private Integer id;

	/**
	* 计划名称
	*/
	private String planName;

	/**
	* 计划简介
	*/
	private String planDesc;

	/**
	* 计划开始时间
	*/
	private Date planStartTime;

	/**
	* 计划结束时间
	*/
	private Date planEndTime;

	/**
	* 目的地
	*/
	private String destination;

	/**
	* 背景图片
	*/
	private String bgImage;

	/**
	* 类别
	*/
	private Integer category;

	/**
	* 状态
	*/
	private Integer state;

	/**
	* 创建人
	*/
	private Integer creator;

	/**
	* 创建时间
	*/
	private Date createTime;

	/**
	* 更新人
	*/
	private Integer updater;

	/**
	* 更新时间
	*/
	private Date updateTime;

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
	* 显示顺序
	*/
	private Integer displayIndex;

}