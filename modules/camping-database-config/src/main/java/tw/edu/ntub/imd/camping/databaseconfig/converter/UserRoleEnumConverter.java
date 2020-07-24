package tw.edu.ntub.imd.camping.databaseconfig.converter;

import tw.edu.ntub.imd.camping.databaseconfig.enumerate.UserRoleEnum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleEnumConverter implements AttributeConverter<UserRoleEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserRoleEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.id;
    }

    @Override
    public UserRoleEnum convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return UserRoleEnum.getByRoleId(dbData);
    }
}
