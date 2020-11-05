package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class NeverHandleProblemReportException extends ProjectException {
    public NeverHandleProblemReportException(int id) {
        super("此問題回報尚未有人處理：" + id);
    }

    @Override
    public String getErrorCode() {
        return "ProblemReport - NotHandle";
    }
}
