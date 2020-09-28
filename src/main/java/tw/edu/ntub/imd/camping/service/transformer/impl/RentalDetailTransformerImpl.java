package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.RentalDetailBean;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;
import tw.edu.ntub.imd.camping.databaseconfig.entity.RentalDetail;
import tw.edu.ntub.imd.camping.service.transformer.ProductTransformer;
import tw.edu.ntub.imd.camping.service.transformer.RentalDetailTransformer;

import javax.annotation.Nonnull;

@Component
public class RentalDetailTransformerImpl implements RentalDetailTransformer {
    private final ProductTransformer productTransformer;

    public RentalDetailTransformerImpl(ProductTransformer productTransformer) {
        this.productTransformer = productTransformer;
    }

    @Nonnull
    @Override
    public RentalDetail transferToEntity(@Nonnull RentalDetailBean rentalDetailBean) {
        return JavaBeanUtils.copy(rentalDetailBean, new RentalDetail());
    }

    @Nonnull
    @Override
    public RentalDetailBean transferToBean(@Nonnull RentalDetail rentalDetail) {
        RentalDetailBean result = JavaBeanUtils.copy(rentalDetail, new RentalDetailBean());
        if (rentalDetail.getProductByProductId() != null) {
            result.setProduct(productTransformer.transferToBean(rentalDetail.getProductByProductId()));
        }
        return result;
    }

    @Override
    public RentalDetail transferProductToEntity(Product product) {
        RentalDetail result = new RentalDetail();
        result.setProductId(product.getId());
        result.setProductByProductId(product);
        return result;
    }
}
