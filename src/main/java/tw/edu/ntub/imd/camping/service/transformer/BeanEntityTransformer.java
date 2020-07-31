package tw.edu.ntub.imd.camping.service.transformer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface BeanEntityTransformer<B, E> {
    @Nonnull
    E transferToEntity(@Nonnull B b);

    @Nonnull
    default List<E> transferToEntityList(@Nonnull List<B> bList) {
        return bList.stream().filter(Objects::nonNull).map(this::transferToEntity).collect(Collectors.toList());
    }

    @Nonnull
    B transferToBean(@Nonnull E e);

    @Nonnull
    default List<B> transferToBeanList(@Nonnull List<E> eList) {
        return eList.stream().filter(Objects::nonNull).map(this::transferToBean).collect(Collectors.toList());
    }
}
