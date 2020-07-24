package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.BaseDAO;
import tw.edu.ntub.imd.camping.service.BaseService;
import tw.edu.ntub.imd.camping.service.transformer.BeanEntityTransformer;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseServiceImpl<D extends BaseDAO<E, ID>, E, ID extends Serializable, B> extends BaseViewServiceImpl<D, E, ID, B> implements BaseService<B, ID> {
    private final D baseDAO;

    public BaseServiceImpl(D d, BeanEntityTransformer<E, B> transformer) {
        super(d, transformer);
        this.baseDAO = d;
    }

    @Transactional
    @Override
    public void update(ID id, B b) {
        try {
            Optional<E> optional = baseDAO.findById(id);
            if (optional.isPresent()) {
                E entity = optional.get();
                JavaBeanUtils.copy(b, entity);
                baseDAO.save(entity);
            } else {
                throw new Exception("找不到資料, id = " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void delete(ID id) {
        try {
            Optional<E> optional = baseDAO.findById(id);
            if (optional.isPresent()) {
                E entity = optional.get();
                baseDAO.delete(entity);
            } else {
                throw new Exception("找不到資料, id = " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
