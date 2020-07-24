package tw.edu.ntub.imd.camping.databaseconfig.dao.impl;

import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.NoRepositoryBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.CountQuerySelector;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.QuerySelector;
import tw.edu.ntub.imd.camping.databaseconfig.dao.criteria.QuerySelectorImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
@NoRepositoryBean
public class BaseCustomDAOImpl<E, ID extends Serializable> {
    @PersistenceContext
    private EntityManager entityManager;
    private final Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public BaseCustomDAOImpl() {
        Class<?>[] genericTypeArray = GenericTypeResolver.resolveTypeArguments(getClass(), BaseCustomDAOImpl.class);
        entityClass = (Class<E>) Objects.requireNonNull(genericTypeArray)[0];
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Class<E> getEntityClass() {
        return entityClass;
    }

    protected QuerySelector<E, E> getQuerySelector() {
        return QuerySelectorImpl.create(entityManager, entityClass);
    }

    protected <R> QuerySelector<E, R> getQuerySelector(SingularAttribute<E, R> attribute) {
        return QuerySelectorImpl.create(entityManager, entityClass, attribute);
    }

    protected <R> QuerySelector<E, R> getQuerySelector(Class<R> resultClass) {
        return QuerySelectorImpl.create(entityManager, entityClass, resultClass);
    }

    protected CountQuerySelector<E> getCountQuerySelector() {
        return new CountQuerySelector<>(QuerySelectorImpl.create(entityManager, entityClass, Long.class));
    }

    protected boolean isEmpty(Object object) {
        return !isNotEmpty(object);
    }

    protected boolean isNotEmpty(Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Character) {
            Character temp = (Character) object;
            return !Character.isSpaceChar(temp);
        } else if (object instanceof String) {
            String temp = (String) object;
            return !temp.isBlank();
        } else if (object instanceof Byte) {
            Byte temp = (Byte) object;
            return temp != 0;
        } else if (object instanceof Short) {
            Short temp = (Short) object;
            return temp != 0;
        } else if (object instanceof Integer) {
            Integer temp = (Integer) object;
            return temp != 0;
        } else if (object instanceof Long) {
            Long temp = (Long) object;
            return temp != 0;
        } else if (object instanceof Float) {
            Float temp = (Float) object;
            return temp != 0.0;
        } else if (object instanceof Double) {
            Double temp = (Double) object;
            return temp != 0.0;
        } else if (object instanceof Collection) {
            Collection<?> temp = (Collection<?>) object;
            return !temp.isEmpty();
        } else {
            return true;
        }
    }
}
