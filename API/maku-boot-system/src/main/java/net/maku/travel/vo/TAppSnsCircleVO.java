package net.maku.travel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import net.maku.framework.common.utils.DateUtils;
import java.util.Date;

/**
* 圈子表
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-12
*/
@Data
@Schema(description = "圈子表")
public class TAppSnsCircleVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@Schema(description = "用户ID")
	private Integer userId;

	@Schema(description = "名称")
	private String name;

	@Schema(description = "头像")
	private String avatar;

	@Schema(description = "简介")
	private String intro;

	@Schema(description = "ip")
	private String ip;

	@Schema(description = "纬度")
	private String lat;

	@Schema(description = "经度")
	private String lng;

	@Schema(description = "国家")
	private String country;

	@Schema(description = "省")
	private String province;

	@Schema(description = "市")
	private String city;

	@Schema(description = "区")
	private String district;

	@Schema(description = "高亮")
	private Integer highlight;

	@Schema(description = "积分")
	private Integer integral;

	@Schema(description = "状态")
	private Integer status;

	@Schema(description = "类型")
	private Integer type;

	@Schema(description = "权重")
	private Integer weigh;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

	@Schema(description = "创建人")
	private Integer creator;

	@Schema(description = "更新时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date updateTime;

	@Schema(description = "更新人")
	private Integer updater;

	@Schema(description = "租户ID")
	private Integer tenantId;

	@Schema(description = "版本号")
	private Integer version;

	@Schema(description = "删除标识  0：正常   1：已删除")
	private Integer deleted;


}
