package net.maku.travel.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

import java.util.Date;

/**
* 用户信息查询
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-10
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户信息查询")
public class SysUserInfoQuery extends Query {
    @Schema(description = "用户id")
    private Long userId;

}