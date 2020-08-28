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
}
