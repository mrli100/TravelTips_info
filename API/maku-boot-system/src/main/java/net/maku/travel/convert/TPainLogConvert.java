package net.maku.travel.convert;

import net.maku.travel.entity.TPainLogEntity;
import net.maku.travel.vo.TPainLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
* 痛苦日志记录
*
* @author 阿沐 babamu@126.com
* @since 1.0.0 2024-04-11
*/
@Mapper
public interface TPainLogConvert {
    TPainLogConvert INSTANCE = Mappers.getMapper(TPainLogConvert.class);

    TPainLogEntity convert(TPainLogVO vo);

    TPainLogVO convert(TPainLogEntity entity);

    List<TPainLogVO> convertList(List<TPainLogEntity> list);

}