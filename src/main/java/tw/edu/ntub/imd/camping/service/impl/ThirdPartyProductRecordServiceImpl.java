package tw.edu.ntub.imd.camping.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductBrandDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductSubTypeDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProductTypeDAO;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ThirdPartyProductRecordDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductBrand;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ThirdPartyProductRecord;
import tw.edu.ntub.imd.camping.dto.file.excel.row.Row;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.service.ThirdPartyProductRecordService;

import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ThirdPartyProductRecordServiceImpl implements ThirdPartyProductRecordService {
    private final ProductTypeDAO productTypeDAO;
    private final ProductBrandDAO productBrandDAO;
    private final ProductSubTypeDAO productSubTypeDAO;
    private final ThirdPartyProductRecordDAO thirdPartyProductRecordDAO;

    @Override
    @Transactional
    public void importRecord(Workbook recordWorkbook) {
        thirdPartyProductRecordDAO.saveAll(
                recordWorkbook.getSheet(0)
                        .getLoadedRowList()
                        .stream()
                        .skip(1)
                        .map(this::createRecord)
                        .collect(Collectors.toList())
        );
    }

    private ThirdPartyProductRecord createRecord(Row row) {
        String brand = row.getCell(0).getValueAsString();
        String type = row.getCell(1).getValueAsString();
        String subType = row.getCell(2).getValueAsString();
        int price = row.getCell(3).getValueAsInt();

        ProductBrand productBrand = getProductBrand(brand);
        ProductType productType = getProductType(type);
        ProductSubType productSubType = getProductSubType(productType, subType);
        ThirdPartyProductRecord record = new ThirdPartyProductRecord();
        record.setBrandId(productBrand.getId());
        record.setSubType(productSubType.getId());
        record.setPrice(price);
        return record;
    }

    private ProductType getProductType(String type) {
        return productTypeDAO.findByNameAndEnableIsTrue(type)
                .orElseThrow(() -> new NotFoundException("未知的商品類型：" + type));
    }

    private ProductSubType getProductSubType(ProductType productType, String subTypeName) {
        Optional<ProductSubType> optionalProductSubType =
                productSubTypeDAO.findByTypeAndName(productType.getId(), subTypeName);
        if (optionalProductSubType.isPresent()) {
            return optionalProductSubType.get();
        } else {
            ProductSubType productSubType = new ProductSubType();
            productSubType.setType(productType.getId());
            productSubType.setName(subTypeName);
            return productSubTypeDAO.saveAndFlush(productSubType);
        }
    }

    private ProductBrand getProductBrand(String brandName) {
        Optional<ProductBrand> optionalProductBrand =
                productBrandDAO.findByName(brandName);
        if (optionalProductBrand.isPresent()) {
            return optionalProductBrand.get();
        } else {
            ProductBrand productBrand = new ProductBrand();
            productBrand.setName(brandName);
            return productBrandDAO.saveAndFlush(productBrand);
        }
    }
}
