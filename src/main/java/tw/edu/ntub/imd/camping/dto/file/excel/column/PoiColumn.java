package tw.edu.ntub.imd.camping.dto.file.excel.column;

import tw.edu.ntub.imd.camping.dto.file.excel.cell.Cell;
import tw.edu.ntub.imd.camping.dto.file.excel.range.Range;
import tw.edu.ntub.imd.camping.dto.file.excel.sheet.Sheet;
import tw.edu.ntub.imd.camping.dto.file.excel.workbook.Workbook;
import tw.edu.ntub.imd.camping.enumerate.RangeFixType;
import tw.edu.ntub.imd.camping.util.excel.ExcelUtils;

public class PoiColumn implements Column {
    private final Sheet sheet;
    private final int index;
    private final String name;
    private final Range columnRange;

    public PoiColumn(Sheet sheet, String columnName) {
        this(sheet, ExcelUtils.formatColumnNameToColumnIndex(columnName), columnName);
    }

    public PoiColumn(Sheet sheet, int index) {
        this(sheet, index, ExcelUtils.formatColumnIndexToEnglish(index));
    }

    public PoiColumn(Sheet sheet, int index, String name) {
        this.sheet = sheet;
        this.index = index;
        this.name = name.toUpperCase();
        this.columnRange = Range.createColumnRange(name);
    }

    @Override
    public PoiColumn getOriginalObject() {
        return this;
    }

    @Override
    public Workbook getWorkbook() {
        return sheet.getWorkbook();
    }

    @Override
    public Sheet getSheet() {
        return sheet;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Cell getCellByRowIndex(int rowIndex) {
        return sheet.getCellByRowIndex(index, rowIndex);
    }

    @Override
    public void clear() {
        sheet.clearColumnContent(index);
    }

    @Override
    public String getRange() {
        return columnRange.getRange();
    }

    @Override
    public void setFixType(RangeFixType type) {
        columnRange.setFixType(type);
    }

    @Override
    public String toString() {
        return getName();
    }
}
