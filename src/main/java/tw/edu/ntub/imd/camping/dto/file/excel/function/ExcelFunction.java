package tw.edu.ntub.imd.camping.dto.file.excel.function;

public interface ExcelFunction extends Reference {
    String getFunctionDefineString();

    boolean isArrayFunction();

    @Override
    default String getReference() {
        return getFunctionDefineString();
    }
}
