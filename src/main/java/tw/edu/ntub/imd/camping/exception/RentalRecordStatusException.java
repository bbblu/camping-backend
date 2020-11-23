package tw.edu.ntub.imd.camping.exception;

import tw.edu.ntub.birc.common.exception.ProjectException;

public abstract class RentalRecordStatusException extends ProjectException {
    public RentalRecordStatusException() {
        super();
    }

    public RentalRecordStatusException(String message) {
        super(message);
    }

    public RentalRecordStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RentalRecordStatusException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getErrorCode() {
        return "RentalRecordStatus - " + getReason();
    }

    public abstract String getReason();
}
