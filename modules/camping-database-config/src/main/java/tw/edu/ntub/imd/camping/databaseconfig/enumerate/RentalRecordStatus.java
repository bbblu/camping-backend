package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

public enum RentalRecordStatus {
    CANCEL("0"),
    NOT_PICK_UP("1"),
    NOT_RETURN("2"),
    RETRIEVE("3"),
    CHECKED("4");

    public final String status;

    RentalRecordStatus(String status) {
        this.status = status;
    }

    public static RentalRecordStatus getByStatus(String status) {
        for (RentalRecordStatus value : values()) {
            if (value.status.equals(status)) {
                return value;
            }
        }
        throw new IllegalStateException("沒有此狀態：" + status);
    }
}
