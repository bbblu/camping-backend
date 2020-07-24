package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProductLaunchedProcessStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProductLaunchedProcessStatusConverter implements AttributeConverter<ProductLaunchedProcessStatus, String> {
    @Override
    public String convertToDatabaseColumn(ProductLaunchedProcessStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.status;
    }

    @Override
    public ProductLaunchedProcessStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return ProductLaunchedProcessStatus.getByStatus(dbData);
    }
}
