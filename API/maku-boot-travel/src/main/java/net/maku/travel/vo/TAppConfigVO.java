package net.maku.travel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import net.maku.framework.common.utils.DateUtils;
import java.util.Date;

/**
* 程序配置
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-01
*/
@Data
@Schema(description = "程序配置")
public class TAppConfigVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@Schema(description = "应用标识")
	private String type;

	@Schema(description = "配置名称")
	private String name;

	@Schema(description = "标识")
	private String keyname;

	@Schema(description = "值")
	private String value;

	@Schema(description = "状态")
	private Object status;

	@Schema(description = "租户ID")
	private Integer tenantId;

	@Schema(description = "版本号")
	private Integer version;

	@Schema(description = "删除标识  0：正常   1：已删除")
	private Integer deleted;

	@Schema(description = "创建者")
	private Integer creator;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

	@Schema(description = "更新者")
	private Integer updater;

	@Schema(description = "更新时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date updateTime;


}