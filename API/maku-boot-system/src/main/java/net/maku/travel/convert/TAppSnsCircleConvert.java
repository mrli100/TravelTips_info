package net.maku.travel.convert;

import net.maku.travel.entity.TAppSnsCircleEntity;
import net.maku.travel.vo.TAppSnsCircleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 圈子表
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-03-12
*/
@Mapper
public interface TAppSnsCircleConvert {
    TAppSnsCircleConvert INSTANCE = Mappers.getMapper(TAppSnsCircleConvert.class);

    TAppSnsCircleEntity convert(TAppSnsCircleVO vo);

    TAppSnsCircleVO convert(TAppSnsCircleEntity entity);

    List<TAppSnsCircleVO> convertList(List<TAppSnsCircleEntity> list);

}
