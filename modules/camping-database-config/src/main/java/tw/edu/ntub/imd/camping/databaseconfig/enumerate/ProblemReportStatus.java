package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 問題回報的處理狀態(0: 未處理/ 1: 處理中/ 2: 已完成)
 *
 * @since 1.4.0
 */
public enum ProblemReportStatus {
    /**
     * 未處理
     *
     * @since 1.4.0
     */
    UNPROCESSED("未處理"),
    /**
     * 處理中
     *
     * @since 1.4.0
     */
    PROCESSED("處理中"),
    /**
     * 已完成
     *
     * @since 1.4.0
     */
    COMPLETE("已完成");

    public final String statusName;

    ProblemReportStatus(String statusName) {
        this.statusName = statusName;
    }
}
