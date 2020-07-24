package tw.edu.ntub.imd.camping.dto.file.excel.range;

import tw.edu.ntub.imd.camping.dto.file.excel.function.Reference;
import tw.edu.ntub.imd.camping.enumerate.RangeFixType;

@SuppressWarnings("unused")
public interface Range extends Reference {
    static Range createColumnRange(String columnName) {
        return new StaticRowOrColumnRange(columnName);
    }

    static Range createRowRange(int rowNumber) {
        return new StaticRowOrColumnRange(String.valueOf(rowNumber));
    }

    String getRange();

    void setFixType(RangeFixType type);

    @Override
    default String getReference() {
        return getRange();
    }
}
