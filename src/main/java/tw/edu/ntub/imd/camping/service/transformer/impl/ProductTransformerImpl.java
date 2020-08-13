package tw.edu.ntub.imd.camping.service.transformer.impl;

import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ProductBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductImageDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductRelatedLinkDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;
import tw.edu.ntub.imd.camping.service.transformer.ProductImageTransformer;
import tw.edu.ntub.imd.camping.service.transformer.ProductRelatedLinkTransformer;
import tw.edu.ntub.imd.camping.service.transformer.ProductTransformer;

import javax.annotation.Nonnull;

@Component
public class ProductTransformerImpl implements ProductTransformer {
    private final ProductImageDAO imageDAO;
    private final ProductImageTransformer imageTransformer;
    private final ProductRelatedLinkDAO relatedLinkDAO;
    private final ProductRelatedLinkTransformer relatedLinkTransformer;

    public ProductTransformerImpl(
            ProductImageDAO imageDAO,
            ProductImageTransformer imageTransformer,
            ProductRelatedLinkDAO relatedLinkDAO,
            ProductRelatedLinkTransformer relatedLinkTransformer
    ) {
        this.imageDAO = imageDAO;
        this.imageTransformer = imageTransformer;
        this.relatedLinkDAO = relatedLinkDAO;
        this.relatedLinkTransformer = relatedLinkTransformer;
    }

    @Nonnull
    @Override
    public Product transferToEntity(@Nonnull ProductBean productBean) {
        return JavaBeanUtils.copy(productBean, new Product());
    }

    @Nonnull
    @Override
    public ProductBean transferToBean(@Nonnull Product product) {
        ProductBean result = JavaBeanUtils.copy(product, new ProductBean());
        if (product.getId() != null) {
            if (CollectionUtils.isEmpty(result.getImageArray())) {
                result.setImageArray(imageTransformer.transferToBeanList(
                        imageDAO.findByProductId(product.getId())
                ));
            }
            if (CollectionUtils.isEmpty(result.getRelatedLinkList())) {
                result.setRelatedLinkList(relatedLinkTransformer.transferToBeanList(
                        relatedLinkDAO.findByProductId(product.getId())
                ));
            }
        }
        return result;
    }
}
