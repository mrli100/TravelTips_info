package net.maku.travel.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

import java.util.Date;

/**
* 计划路线查询
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-10
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "计划路线查询")
public class TPlanTripMainQuery extends Query {
    @Schema(description = "计划名称")
    private String planName;

    @Schema(description = "计划简介")
    private String planDesc;

    @Schema(description = "目的地")
    private String destination;

    @Schema(description = "状态")
    private Integer state;

    @Schema(description = "创建人")
    private Integer creator;

}