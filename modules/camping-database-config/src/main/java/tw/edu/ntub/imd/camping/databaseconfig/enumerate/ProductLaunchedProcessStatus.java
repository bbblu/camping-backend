package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 商品群組上架紀錄的執行狀態(0: 未執行/ 1: 已執行/ 2: 失敗)
 *
 * @see tw.edu.ntub.imd.camping.databaseconfig.entity.ProductLaunchedProcess
 * @since 1.0.0
 */
public enum ProductLaunchedProcessStatus {
    /**
     * 未執行
     *
     * @since 1.0.0
     */
    UNPROCESSED("0"),
    /**
     * 已執行
     *
     * @since 1.0.0
     */
    PROCESSED("1"),
    /**
     * 失敗
     *
     * @since 1.0.0
     */
    FAIL("2");

    /**
     * 狀態編號
     *
     * @since 1.0.0
     */
    public final String status;

    ProductLaunchedProcessStatus(String status) {
        this.status = status;
    }
}
