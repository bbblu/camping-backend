package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.*;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.Product;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductRelatedLink;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.CanBorrowProductGroup;
import tw.edu.ntub.imd.camping.dto.BankAccount;
import tw.edu.ntub.imd.camping.service.ProductGroupService;
import tw.edu.ntub.imd.camping.service.transformer.*;
import tw.edu.ntub.imd.camping.util.OwnerChecker;
import tw.edu.ntub.imd.camping.util.TransactionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductGroupServiceImpl extends BaseServiceImpl<ProductGroupBean, ProductGroup, Integer> implements ProductGroupService {
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
    private final TransactionUtils transactionUtils;

    public ProductGroupServiceImpl(
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
            TransactionUtils transactionUtils) {
        super(groupDAO, transformer);
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
        this.transactionUtils = transactionUtils;
    }

    @Override
    public ProductGroupBean save(ProductGroupBean productGroupBean) {
        ProductGroup productGroup = transformer.transferToEntity(productGroupBean);
        transactionUtils.createBankAccount(new BankAccount(productGroup.getBankAccount()));
        ProductGroup saveResult = groupDAO.saveAndFlush(productGroup);
        if (CollectionUtils.isNotEmpty(productGroupBean.getProductArray())) {
            saveProduct(saveResult.getId(), productGroupBean.getProductArray());
        }
        return transformer.transferToBean(saveResult);
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
                saveImage(saveResult.getId(), productBean.getImageArray());
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

    private void saveImage(int productId, List<ProductImageBean> productImageBeanList) {
        imageDAO.saveAll(productImageBeanList.stream()
                .map(imageTransformer::transferToEntity)
                .peek(image -> image.setProductId(productId))
                .collect(Collectors.toList())
        );
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
        OwnerChecker.checkIsProductGroupOwner(groupDAO, id);
        super.update(id, productGroupBean);
        updateProduct(productGroupBean.getProductArray());
    }

    @Override
    public void updateProduct(List<ProductBean> productBeanList) {
        if (CollectionUtils.isNotEmpty(productBeanList)) {
            List<Integer> idList = productBeanList.stream()
                    .map(ProductBean::getId)
                    .collect(Collectors.toList());
            OwnerChecker.checkIsAllProductOwner(productDAO, idList);
            List<Product> productList = productDAO.findAllById(idList);
            List<Product> newProductList = JavaBeanUtils.copy(productBeanList, productList);
            productDAO.saveAll(newProductList);
        }
    }

    @Override
    public void delete(Integer id) {
        OwnerChecker.checkIsProductGroupOwner(groupDAO, id);
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
        OwnerChecker.checkIsAllProductOwner(productDAO, Collections.singletonList(productId));
        productDAO.updateEnableById(productId, false);
        imageDAO.updateEnableByProductIdList(Collections.singletonList(productId), false);
        relatedLinkDAO.updateEnableByProductIdList(Collections.singletonList(productId), false);
    }

    @Override
    public void deleteProductImage(Integer productImageId) {
        OwnerChecker.checkIsProductImageOwner(imageDAO, productImageId);
        imageDAO.updateEnableById(Collections.singletonList(productImageId), false);
    }

    @Override
    public void deleteProductRelatedLink(Integer productRelatedLinkId) {
        OwnerChecker.checkIsProductRelatedLinkOwner(relatedLinkDAO, productRelatedLinkId);
        relatedLinkDAO.updateEnableById(Collections.singletonList(productRelatedLinkId), false);
    }
}
