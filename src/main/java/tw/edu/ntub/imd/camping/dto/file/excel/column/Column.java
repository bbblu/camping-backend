package tw.edu.ntub.imd.camping.dto.file.excel.column;

import tw.edu.ntub.imd.camping.dto.file.excel.cell.Cell;
import tw.edu.ntub.imd.camping.dto.file.excel.range.Range;
import tw.edu.ntub.imd.camping.dto.file.excel.sheet.Sheet;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;

public interface Column extends Range {
    Object getOriginalObject();

    Workbook getWorkbook();

    Sheet getSheet();

    int getIndex();

    String getName();

    default boolean isNameEquals(String columnName) {
        String name = getName();
        return name.equals(columnName);
    }

    Cell getCellByRowIndex(int rowIndex);

    default Cell getCellByRowNumber(int rowNumber) {
        return getCellByRowIndex(rowNumber - 1);
    }

    void clear();
}
