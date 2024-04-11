package net.maku.travel.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
* 痛苦日志记录查询
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-04-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "痛苦日志记录查询")
public class TPainLogQuery extends Query {
}
