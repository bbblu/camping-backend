package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.imd.camping.databaseconfig.enumerate.Experience;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ExperienceConverter implements AttributeConverter<Experience, String> {
    @Override
    public String convertToDatabaseColumn(Experience attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.id;
    }

    @Override
    public Experience convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Experience.getById(dbData);
    }
}
