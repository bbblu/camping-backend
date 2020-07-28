package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.BaseDAO;
import tw.edu.ntub.imd.camping.service.BaseService;
import tw.edu.ntub.imd.camping.service.transformer.BeanEntityTransformer;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseServiceImpl<B, E, ID extends Serializable> extends BaseViewServiceImpl<B, E, ID> implements BaseService<B, ID> {
    private final BaseDAO<E, ID> baseDAO;

    public BaseServiceImpl(BaseDAO<E, ID> d, BeanEntityTransformer<B, E> transformer) {
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
