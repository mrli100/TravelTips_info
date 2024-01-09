package net.maku.travel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;

import net.maku.framework.common.utils.DateUtils;

import java.util.Date;

/**
 * 计划路线
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2023-12-10
 */
@Data
@Schema(description = "计划路线")
public class TPlanTripMainVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    private Integer id;

    @Schema(description = "计划名称")
    private String planName;

    @Schema(description = "计划简介")
    private String planDesc;

    @Schema(description = "计划开始时间")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_PATTERN, timezone = "UTC")
    private Date planStartTime;

    @Schema(description = "计划结束时间")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_PATTERN, timezone = "UTC")
    private Date planEndTime;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "背景图片")
    private String bgImage;

    @Schema(description = "类别")
    private Integer category;

    @Schema(description = "状态")
    private Integer state;

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

    @Schema(description = "显示顺序")
    private Integer displayIndex;


}
