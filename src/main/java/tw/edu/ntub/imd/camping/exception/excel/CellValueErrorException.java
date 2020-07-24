package tw.edu.ntub.imd.camping.exception.excel;

public class CellValueErrorException extends ExcelException {
    public CellValueErrorException(String error) {
        super("儲存格錯誤: " + error);
    }

    @Override
    public String getReason() {
        return "Error";
    }
}
