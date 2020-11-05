package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class ProblemReportHandlerMismatchException extends ProjectException {
    public ProblemReportHandlerMismatchException() {
        super("處理人與登入者不符合");
    }

    @Override
    public String getErrorCode() {
        return "ProblemReport - HandlerMismatch";
    }
}
