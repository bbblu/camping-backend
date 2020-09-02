package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

public enum RentalRecordStatus {
    CANCEL,
    NOT_PICK_UP,
    NOT_RETURN,
    RETRIEVE,
    CHECKED;

    public RentalRecordStatus next() {
        switch (this) {
            case NOT_PICK_UP:
                return NOT_RETURN;
            case NOT_RETURN:
                return RETRIEVE;
            case RETRIEVE:
                return CHECKED;
            case CANCEL:
            case CHECKED:
            default:
                return null;
        }
    }

    public boolean isCanChangeTo(RentalRecordStatus status) {
        switch (this) {
            case NOT_PICK_UP:
            case NOT_RETURN:
            case RETRIEVE:
                return status == next();
            case CANCEL:
                return true;
            case CHECKED:
            default:
                return false;
        }
    }
}
