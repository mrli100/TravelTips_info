package net.maku.travel.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.mybatis.service.impl.BaseServiceImpl;
import net.maku.travel.convert.TAppConfigConvert;
import net.maku.travel.entity.TAppConfigEntity;
import net.maku.travel.query.TAppConfigQuery;
import net.maku.travel.vo.TAppConfigVO;
import net.maku.travel.dao.TAppConfigDao;
import net.maku.travel.service.TAppConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 程序配置
 *
 * @author 阿沐 babamu@126.com
 * @since 1.0.0 2024-03-01
 */
@Service
@AllArgsConstructor
public class TAppConfigServiceImpl extends BaseServiceImpl<TAppConfigDao, TAppConfigEntity> implements TAppConfigService {

    @Override
    public PageResult<TAppConfigVO> page(TAppConfigQuery query) {
        IPage<TAppConfigEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(TAppConfigConvert.INSTANCE.convertList(page.getRecords()), page.getTotal(), page.getPages());
    }

    private LambdaQueryWrapper<TAppConfigEntity> getWrapper(TAppConfigQuery query){
        LambdaQueryWrapper<TAppConfigEntity> wrapper = Wrappers.lambdaQuery();

        return wrapper;
    }

    @Override
    public void save(TAppConfigVO vo) {
        TAppConfigEntity entity = TAppConfigConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(TAppConfigVO vo) {
        TAppConfigEntity entity = TAppConfigConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

}