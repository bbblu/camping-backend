package tw.edu.ntub.imd.camping.databaseconfig.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class EmptyProblemReportHandleResult extends ProjectException {
    public EmptyProblemReportHandleResult() {
        super("未填寫處理結果");
    }

    @Override
    public String getErrorCode() {
        return "ProblemReport - EmptyHandleResult";
    }
}
