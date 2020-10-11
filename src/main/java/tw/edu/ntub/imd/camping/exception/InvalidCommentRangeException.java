package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public class InvalidCommentRangeException extends ProjectException {
    public InvalidCommentRangeException(byte comment) {
        super("評價分數應為1 <= 評價分數 <= 5：" + comment);
    }

    @Override
    public String getErrorCode() {
        return "Comment - InvalidRange";
    }
}
