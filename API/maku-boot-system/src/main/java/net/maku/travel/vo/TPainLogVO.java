package net.maku.travel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import net.maku.framework.common.utils.DateUtils;
import java.util.Date;

/**
* 痛苦日志记录
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-04-11
*/
@Data
@Schema(description = "痛苦日志记录")
public class TPainLogVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键")
	private Integer id;

	@Schema(description = "QQ号")
	private Integer uid;

	@Schema(description = "访问时间")
	private String times;

	@Schema(description = "访问日期")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date timeData;

	@Schema(description = "hide_from")
	private String hideFrom;

	@Schema(description = "is_hide_visit")
	private String isHideVisit;

	@Schema(description = "QQ昵称")
	private String nickName;

	@Schema(description = "黄钻")
	private String yellow;

	@Schema(description = "是否VIP")
	private String supervip;

	@Schema(description = "是否年费")
	private String annualfee;

	@Schema(description = "访问内容")
	private String photoes;

	@Schema(description = "uins")
	private String uins;

	@Schema(description = "创建人")
	private Integer creator;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date createTime;

	@Schema(description = "更新人")
	private Integer updater;

	@Schema(description = "更新时间")
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	private Date updateTime;

	@Schema(description = "租户ID")
	private Integer tenantId;

	@Schema(description = "版本号")
	private Integer version;

	@Schema(description = "删除标识  0：正常   1：已删除")
	private Integer deleted;


}