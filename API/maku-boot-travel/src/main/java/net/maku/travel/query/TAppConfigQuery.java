package net.maku.travel.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* 程序配置查询
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-01
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "程序配置查询")
public class TAppConfigQuery extends Query {
    @Schema(description = "应用标识")
    private String type;

    @Schema(description = "配置名称")
    private String name;

    @Schema(description = "标识")
    private String keyname;

    @Schema(description = "值")
    private String value;

    @Schema(description = "创建者")
    private Integer creator;
}
