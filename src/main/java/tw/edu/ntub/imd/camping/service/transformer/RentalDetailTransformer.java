package tw.edu.ntub.imd.camping.service.transformer;

import tw.edu.ntub.imd.camping.bean.RentalDetailBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalDetail;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface RentalDetailTransformer extends BeanEntityTransformer<RentalDetailBean, RentalDetail> {
    RentalDetail transferProductToEntity(Product product);

    default List<RentalDetail> transferProductToEntityList(List<Product> productList) {
        return productList.parallelStream()
                .filter(Objects::nonNull)
                .map(this::transferProductToEntity)
                .collect(Collectors.toList());
    }
}
