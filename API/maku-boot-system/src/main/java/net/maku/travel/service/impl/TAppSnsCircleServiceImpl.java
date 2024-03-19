package net.maku.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.travel.convert.TAppSnsCircleConvert;
import net.maku.travel.entity.TAppSnsCircleEntity;
import net.maku.travel.query.TAppSnsCircleQuery;
import net.maku.travel.vo.TAppSnsCircleVO;
import net.maku.travel.dao.TAppSnsCircleDao;
import net.maku.travel.service.TAppSnsCircleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 圈子表
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-12
 */
@Service
@AllArgsConstructor
public class TAppSnsCircleServiceImpl extends BaseServiceImpl<TAppSnsCircleDao, TAppSnsCircleEntity> implements TAppSnsCircleService {

    @Override
    public PageResult<TAppSnsCircleVO> page(TAppSnsCircleQuery query) {
        IPage<TAppSnsCircleEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(TAppSnsCircleConvert.INSTANCE.convertList(page.getRecords()), page.getTotal(), page.getPages());
    }

    private LambdaQueryWrapper<TAppSnsCircleEntity> getWrapper(TAppSnsCircleQuery query){
        LambdaQueryWrapper<TAppSnsCircleEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(TAppSnsCircleVO vo) {
        TAppSnsCircleEntity entity = TAppSnsCircleConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(TAppSnsCircleVO vo) {
        TAppSnsCircleEntity entity = TAppSnsCircleConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}
