package net.maku.travel.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import java.util.Date;

/**
 * 痛苦日志记录
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-04-11
 */

@Data
@TableName("t_pain_log")
public class TPainLogEntity {
	/**
	* 主键
	*/
	@TableId
	private Integer id;

	/**
	* QQ号
	*/
	private Integer uid;

	/**
	* 访问时间
	*/
	private String times;

	/**
	* 访问日期
	*/
	private Date timeData;

	/**
	* hide_from
	*/
	private String hideFrom;

	/**
	* is_hide_visit
	*/
	private String isHideVisit;

	/**
	* QQ昵称
	*/
	private String nickName;

	/**
	* 黄钻
	*/
	private String yellow;

	/**
	* 是否VIP
	*/
	private String supervip;

	/**
	* 是否年费
	*/
	private String annualfee;

	/**
	* 访问内容
	*/
	private String photoes;

	/**
	* uins
	*/
	private String uins;

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

}