package net.maku.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.maku.framework.common.query.Query;

/**
* 用户信息表查询
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-05
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户信息表查询")
public class SysUserInfoQuery extends Query {
}
