package tw.edu.ntub.imd.camping.databaseconfig.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NoProblemReportHandlerException extends ProjectException {
    public NoProblemReportHandlerException() {
        super("處理人不得為空");
    }

    @Override
    public String getErrorCode() {
        return "ProblemReport - NoHandler";
    }
}
