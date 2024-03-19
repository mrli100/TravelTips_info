package net.maku.travel.convert;

import net.maku.travel.entity.TAppConfigEntity;
import net.maku.travel.vo.TAppConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 程序配置
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-01
*/
@Mapper
public interface TAppConfigConvert {
    TAppConfigConvert INSTANCE = Mappers.getMapper(TAppConfigConvert.class);

    TAppConfigEntity convert(TAppConfigVO vo);

    TAppConfigVO convert(TAppConfigEntity entity);

    List<TAppConfigVO> convertList(List<TAppConfigEntity> list);

}