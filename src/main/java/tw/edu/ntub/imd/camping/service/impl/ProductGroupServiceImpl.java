package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.BooleanUtils;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.*;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.CanBorrowProductGroup;
import tw.edu.ntub.imd.camping.dto.file.uploader.MultipartFileUploader;
import tw.edu.ntub.imd.camping.dto.file.uploader.UploadResult;
import tw.edu.ntub.imd.camping.exception.NotContactInformationOwnerException;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.exception.NotProductGroupOwner;
import tw.edu.ntub.imd.camping.exception.NotProductOwner;
import tw.edu.ntub.imd.camping.service.ProductGroupService;
import tw.edu.ntub.imd.camping.service.transformer.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductGroupServiceImpl extends BaseServiceImpl<ProductGroupBean, ProductGroup, Integer> implements ProductGroupService {
    private final MultipartFileUploader uploader;
    private final ProductGroupDAO groupDAO;
    private final ProductGroupTransformer transformer;
    private final ProductDAO productDAO;
    private final ProductTransformer productTransformer;
    private final ProductTypeDAO typeDAO;
    private final ProductTypeTransformer typeTransformer;
    private final ProductImageDAO imageDAO;
    private final ProductImageTransformer imageTransformer;
    private final ProductRelatedLinkDAO relatedLinkDAO;
    private final CanBorrowProductGroupDAO canBorrowProductGroupDAO;
    private final CanBorrowProductGroupBeanTransformer canBorrowProductGroupBeanTransformer;
    private final ContactInformationDAO contactInformationDAO;

    public ProductGroupServiceImpl(
            MultipartFileUploader uploader,
            ProductGroupDAO groupDAO,
            ProductGroupTransformer transformer,
            ProductDAO productDAO,
            ProductTransformer productTransformer,
            ProductTypeDAO typeDAO,
            ProductTypeTransformer typeTransformer,
            ProductImageDAO imageDAO,
            ProductImageTransformer imageTransformer,
            ProductRelatedLinkDAO relatedLinkDAO,
            CanBorrowProductGroupDAO canBorrowProductGroupDAO,
            CanBorrowProductGroupBeanTransformer canBorrowProductGroupBeanTransformer,
            ContactInformationDAO contactInformationDAO
    ) {
        super(groupDAO, transformer);
        this.uploader = uploader;
        this.groupDAO = groupDAO;
        this.transformer = transformer;
        this.productDAO = productDAO;
        this.productTransformer = productTransformer;
        this.typeDAO = typeDAO;
        this.typeTransformer = typeTransformer;
        this.imageDAO = imageDAO;
        this.imageTransformer = imageTransformer;
        this.relatedLinkDAO = relatedLinkDAO;
        this.canBorrowProductGroupDAO = canBorrowProductGroupDAO;
        this.canBorrowProductGroupBeanTransformer = canBorrowProductGroupBeanTransformer;
        this.contactInformationDAO = contactInformationDAO;
    }

    @Override
    public ProductGroupBean save(ProductGroupBean productGroupBean) {
        checkContactInformationOwner(productGroupBean.getContactInformationId());
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

    private void checkContactInformationOwner(int contactInformationId) {
        Optional<ContactInformation> optionalContactInformation =
                contactInformationDAO.findById(contactInformationId);
        ContactInformation contactInformation =
                optionalContactInformation.orElseThrow(() -> new NotFoundException("找不到對應的聯絡方式：" + contactInformationId));
        if (StringUtils.isNotEquals(SecurityUtils.getLoginUserAccount(), contactInformation.getUserAccount())) {
            throw new NotContactInformationOwnerException(contactInformation.getId(), SecurityUtils.getLoginUserAccount());
        }
    }

    private void saveProduct(int groupId, List<ProductBean> productBeanList) {
        List<ProductBean> saveResultList = saveProduct(productBeanList.parallelStream()
                .peek(productBean -> productBean.setGroupId(groupId))
                .collect(Collectors.toList())
        );
        for (int i = 0; i < productBeanList.size(); i++) {
            ProductBean saveResult = saveResultList.get(i);
            ProductBean productBean = productBeanList.get(i);
            if (CollectionUtils.isNotEmpty(productBean.getImageArray())) {
                saveImage(saveResult.getGroupId(), saveResult.getId(), productBean.getImageArray());
            }
            if (CollectionUtils.isNotEmpty(productBean.getRelatedLinkArray())) {
                saveRelatedLinkUrl(saveResult.getId(), productBean.getRelatedLinkArray());
            }
        }
    }

    private List<ProductBean> saveProduct(List<ProductBean> productBeanList) {
        List<Product> saveResult = productDAO.saveAll(
                productTransformer.transferToEntityList(productBeanList)
        );
        return productTransformer.transferToBeanList(saveResult);
    }

    private void saveImage(int groupId, int productId, List<ProductImageBean> productImageBeanList) {
        String productGroupIdString = String.valueOf(groupId);
        String productIdString = String.valueOf(productId);
        List<ProductImage> productImageList = new ArrayList<>();
        for (ProductImageBean productImageBean : productImageBeanList) {
            ProductImage productImage = imageTransformer.transferToEntity(productImageBean);
            productImage.setProductId(productId);
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

    private void saveRelatedLinkUrl(int productId, List<String> urlList) {
        saveRelatedLink(
                productId,
                urlList.stream()
                        .map(ProductRelatedLink::new)
                        .collect(Collectors.toList())
        );
    }

    private void saveRelatedLink(int productId, List<ProductRelatedLink> relatedLinkList) {
        relatedLinkDAO.saveAll(
                relatedLinkList.stream()
                        .peek(productRelatedLink -> productRelatedLink.setProductId(productId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void update(Integer id, ProductGroupBean productGroupBean) {
        if (BooleanUtils.isFalse(groupDAO.existsByIdAndCreateAccount(id, SecurityUtils.getLoginUserAccount()))) {
            throw new NotProductGroupOwner(id, SecurityUtils.getLoginUserAccount());
        }
        super.update(id, productGroupBean);
        updateProduct(productGroupBean.getProductArray());
    }

    @Override
    public void updateProduct(List<ProductBean> productBeanList) {
        if (CollectionUtils.isNotEmpty(productBeanList)) {
            List<Integer> idList = productBeanList.stream()
                    .map(ProductBean::getId)
                    .collect(Collectors.toList());
            if (BooleanUtils.isFalse(
                    productDAO.existsByIdInAndProductGroupByGroupId_CreateAccount(idList, SecurityUtils.getLoginUserAccount())
            )) {
                throw new NotProductOwner(SecurityUtils.getLoginUserAccount());
            }
            List<Product> productList = productDAO.findAllById(idList);
            List<Product> newProductList = JavaBeanUtils.copy(productBeanList, productList);
            productDAO.saveAll(newProductList);
        }
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
    public List<ProductTypeBean> searchAllProductType() {
        return typeTransformer.transferToBeanList(typeDAO.findByEnableIsTrue());
    }

    @Override
    public List<CanBorrowProductGroupBean> searchCanBorrowProductGroup(ProductGroupFilterDataBean filterData) {
        return canBorrowProductGroupBeanTransformer.transferToBeanList(
                canBorrowProductGroupDAO.findAll()
                        .stream()
                        .filter(filterCanBorrowProductGroup(filterData))
                        .collect(Collectors.toList())
        );
    }

    private Predicate<CanBorrowProductGroup> filterCanBorrowProductGroup(ProductGroupFilterDataBean filterData) {
        return canBorrowProductGroup ->
                filterData.isBorrowStartDateNullOrBefore(canBorrowProductGroup.getBorrowStartDate().toLocalDate()) &&
                        filterData.isBorrowEndDateNullOrAfter(canBorrowProductGroup.getBorrowEndDate().toLocalDate()) &&
                        filterData.isCityAreaNameNullOrEquals(canBorrowProductGroup.getCityAreaName()) &&
                        filterData.isTypeArrayNullOrAllMatchContains(canBorrowProductGroup.getProductTypeArray()) &&
                        filterData.isPriceNullOrBetween(canBorrowProductGroup.getPrice());
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
