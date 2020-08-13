package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.camping.bean.ProductBean;
import tw.edu.ntub.imd.camping.bean.ProductGroupBean;
import tw.edu.ntub.imd.camping.bean.ProductImageBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductGroupDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductImageDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductRelatedLinkDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductImage;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductRelatedLink;
import tw.edu.ntub.imd.camping.dto.file.uploader.MultipartFileUploader;
import tw.edu.ntub.imd.camping.dto.file.uploader.UploadResult;
import tw.edu.ntub.imd.camping.service.ProductGroupService;
import tw.edu.ntub.imd.camping.service.transformer.ProductGroupTransformer;
import tw.edu.ntub.imd.camping.service.transformer.ProductImageTransformer;
import tw.edu.ntub.imd.camping.service.transformer.ProductTransformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductGroupServiceImpl extends BaseServiceImpl<ProductGroupBean, ProductGroup, Integer> implements ProductGroupService {
    private final MultipartFileUploader uploader;
    private final ProductGroupDAO groupDAO;
    private final ProductGroupTransformer transformer;
    private final ProductDAO productDAO;
    private final ProductTransformer productTransformer;
    private final ProductImageDAO imageDAO;
    private final ProductImageTransformer imageTransformer;
    private final ProductRelatedLinkDAO relatedLinkDAO;

    public ProductGroupServiceImpl(
            MultipartFileUploader uploader,
            ProductGroupDAO groupDAO,
            ProductGroupTransformer transformer,
            ProductDAO productDAO,
            ProductTransformer productTransformer,
            ProductImageDAO imageDAO,
            ProductImageTransformer imageTransformer,
            ProductRelatedLinkDAO relatedLinkDAO
    ) {
        super(groupDAO, transformer);
        this.uploader = uploader;
        this.groupDAO = groupDAO;
        this.transformer = transformer;
        this.productDAO = productDAO;
        this.productTransformer = productTransformer;
        this.imageDAO = imageDAO;
        this.imageTransformer = imageTransformer;
        this.relatedLinkDAO = relatedLinkDAO;
    }

    @Override
    public ProductGroupBean save(ProductGroupBean productGroupBean) {
        ProductGroup productGroup = transformer.transferToEntity(productGroupBean);
        ProductGroup saveResult = groupDAO.saveAndFlush(productGroup);
        if (productGroupBean.getCoverImageFile() != null) {
            UploadResult uploadResult =
                    uploader.upload(productGroupBean.getCoverImageFile(), "product-group", String.valueOf(saveResult.getId()));
            saveResult.setCoverImage(uploadResult.getUrl());
            groupDAO.save(saveResult);
        }
        if (CollectionUtils.isNotEmpty(productGroupBean.getProductArray())) {
            saveProduct(saveResult.getId(), productGroupBean.getProductArray());
        }
        return transformer.transferToBean(saveResult);
    }

    private void saveProduct(int groupId, List<ProductBean> productBeanList) {
        List<Product> saveResult = productDAO.saveAll(productTransformer.transferToEntityList(
                productBeanList.stream()
                        .peek(productBean -> productBean.setGroupId(groupId))
                        .collect(Collectors.toList())
        ));
        for (int i = 0; i < productBeanList.size(); i++) {
            saveProduct(saveResult.get(i), productBeanList.get(i));
        }
    }

    private void saveProduct(Product product, ProductBean productBean) {
        if (CollectionUtils.isNotEmpty(productBean.getImageArray())) {
            saveImage(product, productBean.getImageArray());
        }
        if (CollectionUtils.isNotEmpty(productBean.getRelatedLinkArray())) {
            saveRelatedLink(product.getId(), productBean.getRelatedLinkArray());
        }
    }

    private void saveImage(Product product, List<ProductImageBean> productImageBeanList) {
        String productGroupIdString = String.valueOf(product.getGroupId());
        String productIdString = String.valueOf(product.getId());
        List<ProductImage> productImageList = new ArrayList<>();
        for (ProductImageBean productImageBean : productImageBeanList) {
            ProductImage productImage = imageTransformer.transferToEntity(productImageBean);
            productImage.setProductId(product.getId());
            String imageUrl;
            if (productImageBean.getImageFile() != null) {
                UploadResult uploadResult =
                        uploader.upload(
                                productImageBean.getImageFile(),
                                "product-group", productGroupIdString, "product", productIdString
                        );
                imageUrl = uploadResult.getUrl();
            } else {
                imageUrl = productImageBean.getUrl();
            }
            productImage.setUrl(imageUrl);
            productImageList.add(productImage);
        }
        imageDAO.saveAll(productImageList);
    }

    private void saveRelatedLink(int productId, List<String> urlList) {
        relatedLinkDAO.saveAll(
                urlList.stream()
                        .map(ProductRelatedLink::new)
                        .peek(productRelatedLink -> productRelatedLink.setProductId(productId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void delete(Integer id) {
        List<Product> productList = productDAO.findByGroupId(id);
        List<Integer> productIdList = productList.stream().map(Product::getId).collect(Collectors.toList());
        imageDAO.updateEnableByProductIdList(productIdList, false);
        relatedLinkDAO.updateEnableByProductIdList(productIdList, false);
        productDAO.updateEnableByGroupId(id, false);
        groupDAO.updateEnableById(id, false);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDAO.updateEnableById(productId, false);
        imageDAO.updateEnableByProductIdList(Collections.singletonList(productId), false);
        relatedLinkDAO.updateEnableByProductIdList(Collections.singletonList(productId), false);
    }

    @Override
    public void deleteProductImage(Integer productImageId) {
        imageDAO.updateEnableById(Collections.singletonList(productImageId), false);
    }

    @Override
    public void deleteProductRelatedLink(Integer productRelatedLinkId) {
        relatedLinkDAO.updateEnableById(Collections.singletonList(productRelatedLinkId), false);
    }
}
