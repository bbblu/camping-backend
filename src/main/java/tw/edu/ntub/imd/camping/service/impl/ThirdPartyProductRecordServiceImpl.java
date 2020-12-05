package tw.edu.ntub.imd.camping.service.impl;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.birc.common.util.JavaBeanUtils;
import tw.edu.ntub.imd.camping.bean.ThirdPartyProductRecordIndexBean;
import tw.edu.ntub.imd.camping.bean.ThirdPartyProductRecordIndexFilterBean;
import tw.edu.ntub.imd.camping.databaseconfig.dao.*;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductBrand;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductSubType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductType;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ThirdPartyProductRecord;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.ThirdPartyProductRecordIndex;
import tw.edu.ntub.imd.camping.databaseconfig.entity.view.ThirdPartyProductRecordIndex_;
import tw.edu.ntub.imd.camping.dto.file.excel.row.Row;
import tw.edu.ntub.imd.camping.dto.file.excel.sheet.Sheet;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.PoiWorkbook;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;
import tw.edu.ntub.imd.camping.enumerate.ExcelType;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.service.ThirdPartyProductRecordService;
import tw.edu.ntub.imd.camping.service.transformer.ThirdPartyProductRecordIndexTransformer;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ThirdPartyProductRecordServiceImpl implements ThirdPartyProductRecordService {
    private final ProductTypeDAO productTypeDAO;
    private final ProductBrandDAO productBrandDAO;
    private final ProductSubTypeDAO productSubTypeDAO;
    private final ThirdPartyProductRecordDAO thirdPartyProductRecordDAO;
    private final ThirdPartyProductRecordIndexDAO indexDAO;
    private final ThirdPartyProductRecordIndexTransformer indexTransformer;

    @Override
    public List<ThirdPartyProductRecordIndexBean> searchIndexRecord(ThirdPartyProductRecordIndexFilterBean filterBean) {
        return CollectionUtils.map(
                indexDAO.findAll(
                        Example.of(
                                JavaBeanUtils.copy(filterBean, new ThirdPartyProductRecordIndex())
                        ),
                        Sort.by(
                                Sort.Order.asc(ThirdPartyProductRecordIndex_.BRAND_ID),
                                Sort.Order.asc(ThirdPartyProductRecordIndex_.TYPE),
                                Sort.Order.asc(ThirdPartyProductRecordIndex_.SUB_TYPE)
                        )
                ),
                indexTransformer::transferToBean
        );
    }

    @Override
    public Workbook getTemplateExcel() {
        Workbook result = new PoiWorkbook(ExcelType.XLSX, "商家資料匯入檔");
        Sheet dataSheet = result.getSheet("資料");
        dataSheet.setCellValue("A1", "品牌");
        dataSheet.setCellValue("B1", "商品類型");
        dataSheet.setCellValue("C1", "子類型");
        dataSheet.setCellValue("D1", "價格");

        String productTypeColumn = "G";
        dataSheet.setCellValue(productTypeColumn + "1", "商品類型");
        List<ProductType> productTypeList = productTypeDAO.findByEnableIsTrue();
        for (int i = 0; i < productTypeList.size(); i++) {
            ProductType productType = productTypeList.get(i);
            dataSheet.setCellValue(productTypeColumn + (i + 2), productType.getName());
        }
        return result;
    }

    @Override
    @Transactional
    public void importRecord(Workbook recordWorkbook, boolean isReplaceOldRecord) {
        if (isReplaceOldRecord) {
            thirdPartyProductRecordDAO.deleteAll();
        }
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

    private ProductSubType getProductSubType(@NotNull ProductType productType, String subTypeName) {
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

    @Override
    public Workbook getRecordExcel() {
        Workbook result = getTemplateExcel();
        Sheet sheet = result.getSheet(0);

        int rowNumber = 2;
        for (ThirdPartyProductRecordIndex index : indexDAO.findAll()) {
            Row row = sheet.getRowByRowNumber(rowNumber++);
            row.getCell("A").setValue(index.getBrandName());
            row.getCell("B").setValue(index.getTypeName());
            row.getCell("C").setValue(index.getSubTypeName());
            row.getCell("D").setValue(index.getPrice());
        }

        return result;
    }
}
