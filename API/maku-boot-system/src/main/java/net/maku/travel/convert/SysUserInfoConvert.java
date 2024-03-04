package net.maku.travel.convert;

import net.maku.travel.entity.SysUserInfoEntity;
import net.maku.travel.vo.SysUserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 用户信息
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-04
*/
@Mapper
public interface SysUserInfoConvert {
    SysUserInfoConvert INSTANCE = Mappers.getMapper(SysUserInfoConvert.class);

    SysUserInfoEntity convert(SysUserInfoVO vo);

    SysUserInfoVO convert(SysUserInfoEntity entity);

    List<SysUserInfoVO> convertList(List<SysUserInfoEntity> list);

}