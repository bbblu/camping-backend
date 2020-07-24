package tw.edu.ntub.imd.camping.dto.file.excel.function;

public class ExcelFunctionValue implements ExcelFunction {
    private final String functionText;

    public ExcelFunctionValue(String functionText) {
        this.functionText = functionText;
    }

    @Override
    public String getFunctionDefineString() {
        return functionText;
    }

    @Override
    public boolean isArrayFunction() {
        return functionText.startsWith("{") && functionText.endsWith("}");
    }
}
