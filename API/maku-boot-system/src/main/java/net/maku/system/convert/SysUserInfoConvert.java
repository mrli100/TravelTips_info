package net.maku.system.convert;

import net.maku.system.entity.SysUserInfoEntity;
import net.maku.system.vo.SysUserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 用户信息表
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-05
*/
@Mapper
public interface SysUserInfoConvert {
    SysUserInfoConvert INSTANCE = Mappers.getMapper(SysUserInfoConvert.class);

    SysUserInfoEntity convert(SysUserInfoVO vo);

    SysUserInfoVO convert(SysUserInfoEntity entity);

    List<SysUserInfoVO> convertList(List<SysUserInfoEntity> list);

}
