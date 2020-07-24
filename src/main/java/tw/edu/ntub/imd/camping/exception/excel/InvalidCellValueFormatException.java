package tw.edu.ntub.imd.camping.exception.excel;

import tw.edu.ntub.imd.camping.dto.file.excel.cell.Cell;
import tw.edu.ntub.imd.camping.enumerate.CellType;

public class InvalidCellValueFormatException extends ExcelException {
    public InvalidCellValueFormatException(Cell cell, CellType expectedType) {
        super(cell.getCellName() + "類型不是" + expectedType + "，類型為" + cell.getType());
    }

    @Override
    public String getReason() {
        return "InvalidType";
    }
}
