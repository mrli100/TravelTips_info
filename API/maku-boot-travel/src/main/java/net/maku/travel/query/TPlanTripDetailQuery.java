package net.maku.travel.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

import java.math.BigDecimal;
import java.util.Date;

/**
* 计划明细查询
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-01-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "计划明细查询")
public class TPlanTripDetailQuery extends Query {
    @Schema(description = "主键id")
    private Integer id;

    @Schema(description = "计划id")
    private Integer planId;

    @Schema(description = "行程天数")
    private Integer tripDay;

    @Schema(description = "景点名称")
    private String tripName;

    @Schema(description = "景点类型")
    private Integer tripType;

    @Schema(description = "简介")
    private String description;

    @Schema(description = "行程提醒")
    private Integer tripDayNotice;

    @Schema(description = "到达时间")
    private Date reachTime;

    @Schema(description = "停留时间")
    private Integer stopTime;

    @Schema(description = "预计消费")
    private BigDecimal estimateCost;

    @Schema(description = "备注")
    private String notes;

    @Schema(description = "介绍图片")
    private String bgImage;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "坐标")
    private String location;

    @Schema(description = "显示顺序")
    private Integer displayIndex;

    @Schema(description = "上个距离")
    private BigDecimal distance;

    @Schema(description = "租户ID")
    private Integer tenantId;

    @Schema(description = "版本号")
    private Integer version;

    @Schema(description = "删除标识  0：正常   1：已删除")
    private Integer deleted;

    @Schema(description = "创建者")
    private Integer creator;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新者")
    private Date updater;

    @Schema(description = "更新时间")
    private Date updateTime;

}