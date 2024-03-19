package net.maku.travel.convert;

import net.maku.travel.entity.TPlanTripDetailEntity;
import net.maku.travel.vo.TPlanTripDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 计划明细
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-01-28
 */
@Mapper
public interface TPlanTripDetailConvert {
    TPlanTripDetailConvert INSTANCE = Mappers.getMapper(TPlanTripDetailConvert.class);

    TPlanTripDetailEntity convert(TPlanTripDetailVO vo);

    TPlanTripDetailVO convert(TPlanTripDetailEntity entity);

    List<TPlanTripDetailVO> convertList(List<TPlanTripDetailEntity> list);

}