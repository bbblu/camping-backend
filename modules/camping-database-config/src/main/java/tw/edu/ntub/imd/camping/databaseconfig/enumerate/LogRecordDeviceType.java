package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

public enum LogRecordDeviceType {
    UNKNOWN("-1"),
    POSTMAN("00"),
    IE("01"),
    EDGE("02"),
    CHROME("03"),
    FIRE_FOX("04"),
    SAFARI("05"),
    OPERA("06"),
    ANDROID("07"),
    IOS("08");

    public final String deviceType;

    LogRecordDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public static LogRecordDeviceType getInstance(String deviceType) {
        for (LogRecordDeviceType value : values()) {
            if (value.deviceType.equals(deviceType)) {
                return value;
            }
        }
        throw new IllegalStateException("未知的設備類型");
    }
}
