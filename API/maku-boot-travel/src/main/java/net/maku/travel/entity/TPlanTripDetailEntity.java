package net.maku.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 计划明细
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-01-28
 */

@Data
@TableName("t_plan_trip_detail")
public class TPlanTripDetailEntity {
	/**
	* 主键id
	*/
	@TableId
	private Integer id;

	/**
	* 计划id
	*/
	private Integer planId;

	/**
	* 行程天数
	*/
	private Integer tripDay;

	/**
	* 景点名称
	*/
	private String tripName;

	/**
	* 景点类型
	*/
	private Integer tripType;

	/**
	* 简介
	*/
	private String description;

	/**
	* 行程提醒
	*/
	private Integer tripDayNotice;

	/**
	* 到达时间
	*/
	private Date reachTime;

	/**
	* 停留时间
	*/
	private Integer stopTime;

	/**
	* 预计消费
	*/
	private BigDecimal estimateCost;

	/**
	* 备注
	*/
	private String notes;

	/**
	* 介绍图片
	*/
	private String bgImage;

	/**
	* 地址
	*/
	private String address;

	/**
	* 坐标
	*/
	private String location;

	/**
	* 显示顺序
	*/
	private Integer displayIndex;

	/**
	* 上个距离
	*/
	private BigDecimal distance;

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
	private String createTime;

	/**
	* 更新者
	*/
	private Date updater;

	/**
	* 更新时间
	*/
	private Date updateTime;

}