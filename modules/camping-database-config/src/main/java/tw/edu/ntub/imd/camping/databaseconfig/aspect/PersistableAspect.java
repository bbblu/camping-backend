package tw.edu.ntub.imd.camping.databaseconfig.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Persistable;

@Aspect
@Component
public class PersistableAspect {
    @Pointcut("execution(" +
            "public * *+.save(Object)" +
            ") || execution(" +
            "public * *+.saveAndFlush(Object)" +
            ") || execution(" +
            "public * *+.saveAll(Iterable<Object>)" +
            ")")
    public void savePointcut() {
    }

    @Before("savePointcut()")
    @SuppressWarnings("rawtypes")
    public void beforeSave(JoinPoint joinPoint) {
        Object saveEntity = joinPoint.getArgs()[0];
        if (saveEntity instanceof Iterable) {
            Iterable persistableIterable = (Iterable) saveEntity;
            for (Object persistable : persistableIterable) {
                if (persistable instanceof Persistable<?>) {
                    ((Persistable<?>) persistable).setSave(true);
                }
            }
        } else if (saveEntity instanceof Persistable<?>) {
            ((Persistable<?>) saveEntity).setSave(true);
        }
    }
}
