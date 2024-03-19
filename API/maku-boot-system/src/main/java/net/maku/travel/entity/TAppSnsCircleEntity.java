package net.maku.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 圈子表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-12
 */

@Data
@TableName("t_app_sns_circle")
public class TAppSnsCircleEntity {
	@TableId
	private Long id;

	/**
	* 用户ID
	*/
	private Integer userId;

	/**
	* 名称
	*/
	private String name;

	/**
	* 头像
	*/
	private String avatar;

	/**
	* 简介
	*/
	private String intro;

	/**
	* ip
	*/
	private String ip;

	/**
	* 纬度
	*/
	private String lat;

	/**
	* 经度
	*/
	private String lng;

	/**
	* 国家
	*/
	private String country;

	/**
	* 省
	*/
	private String province;

	/**
	* 市
	*/
	private String city;

	/**
	* 区
	*/
	private String district;

	/**
	* 高亮
	*/
	private Integer highlight;

	/**
	* 积分
	*/
	private Integer integral;

	/**
	* 状态
	*/
	private Integer status;

	/**
	* 类型
	*/
	private Integer type;

	/**
	* 权重
	*/
	private Integer weigh;

	/**
	* 创建时间
	*/
	private Date createTime;

	/**
	* 创建人
	*/
	private Integer creator;

	/**
	* 更新时间
	*/
	private Date updateTime;

	/**
	* 更新人
	*/
	private Integer updater;

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

}
