package net.maku.travel.convert;

import net.maku.travel.entity.TPlanTripMainEntity;
import net.maku.travel.vo.TPlanTripMainVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 计划路线
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2023-12-10
*/
@Mapper
public interface TPlanTripMainConvert {
    TPlanTripMainConvert INSTANCE = Mappers.getMapper(TPlanTripMainConvert.class);

    TPlanTripMainEntity convert(TPlanTripMainVO vo);

    TPlanTripMainVO convert(TPlanTripMainEntity entity);

    List<TPlanTripMainVO> convertList(List<TPlanTripMainEntity> list);

}