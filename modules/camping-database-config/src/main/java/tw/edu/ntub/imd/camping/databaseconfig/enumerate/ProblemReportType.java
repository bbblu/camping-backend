package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 問題回報的問題類型(0: 帳號相關/ 1: 租借相關/ 2: 款項相關/ 3: 其他)
 *
 * @since 1.4.9
 */
public enum ProblemReportType {
    /**
     * 帳號相關
     *
     * @since 1.4.9
     */
    ACCOUNT("0"),
    /**
     * 租借相關
     *
     * @since 1.4.9
     */
    RENTAL("1"),
    /**
     * 款項相關
     *
     * @since 1.4.9
     */
    BANK("2"),
    /**
     * 其他
     *
     * @since 1.4.9
     */
    OTHER("3");

    public final String id;

    ProblemReportType(String id) {
        this.id = id;
    }

    public static ProblemReportType getById(String id) {
        for (ProblemReportType value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        throw new IllegalStateException("找不到對應的問題類型：" + id);
    }

    @Override
    public String toString() {
        switch (this) {
            case ACCOUNT:
                return "帳號相關";
            case RENTAL:
                return "租借相關";
            case BANK:
                return "款項相關";
            default:
                return "其他";
        }
    }
}
