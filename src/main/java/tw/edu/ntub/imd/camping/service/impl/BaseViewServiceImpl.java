package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import tw.edu.ntub.imd.camping.databaseconfig.dao.BaseViewDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.camping.service.BaseViewService;
import tw.edu.ntub.imd.camping.service.transformer.BeanEntityTransformer;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseViewServiceImpl<D extends BaseViewDAO<E, ID>, E, ID extends Serializable, B> implements BaseViewService<B, ID> {
    private final D baseDAO;
    private final BeanEntityTransformer<E, B> transformer;

    public BaseViewServiceImpl(D d, BeanEntityTransformer<E, B> transformer) {
        Assert.notNull(d, "baseDAO不能為null");
        Assert.notNull(d, "transformer不能為null");
        this.baseDAO = d;
        this.transformer = transformer;
    }

    @Override
    public Optional<B> getById(ID id) {
        Optional<E> optional = baseDAO.findById(id);
        if (optional.isPresent()) {
            E entity = optional.get();
            B bean = transformer.transferToBean(entity);
            return Optional.of(bean);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<B> searchAll() {
        return transformer.transferToBeanList(baseDAO.findAll());
    }

    @Override
    public List<B> searchAll(Pager pager) {
        PageRequest pageRequest = PageRequest.of(pager.getPage(), pager.getCount());
        return transformer.transferToBeanList(baseDAO.findAll(pageRequest).getContent());
    }

    @Override
    public List<B> searchByBean(B b) {
        List<E> eList = baseDAO.findAll(Example.of(transformer.transferToEntity(b)));
        return transformer.transferToBeanList(eList);
    }

    @Override
    public Optional<B> getByBean(B b) {
        Optional<E> optional = baseDAO.findOne(Example.of(transformer.transferToEntity(b)));
        return optional.map(transformer::transferToBean);
    }
}
