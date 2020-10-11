package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class DuplicateCommentException extends ProjectException {
    public DuplicateCommentException() {
        super("重複評價");
    }

    @Override
    public String getErrorCode() {
        return "Comment - Duplicate";
    }
}
