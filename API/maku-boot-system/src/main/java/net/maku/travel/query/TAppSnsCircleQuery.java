package net.maku.travel.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* 圈子表查询
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "圈子表查询")
public class TAppSnsCircleQuery extends Query {
    @Schema(description = "创建人")
    private Integer creator;

}
