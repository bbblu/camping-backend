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
    ACCOUNT,
    /**
     * 租借相關
     *
     * @since 1.4.9
     */
    RENTAL,
    /**
     * 款項相關
     *
     * @since 1.4.9
     */
    BANK,
    /**
     * 其他
     *
     * @since 1.4.9
     */
    OTHER
}
