package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 租借詳細記錄狀態
 *
 * @see tw.edu.ntub.imd.camping.databaseconfig.entity.RentalDetail
 * @since 1.0.0
 */
public enum RentalDetailStatus {
    /**
     * 未歸還
     *
     * @since 1.0.0
     */
    NOT_RETURN("0"),
    /**
     * 已歸還
     *
     * @since 1.0.0
     */
    RETURNED("1"),
    /**
     * 損壞
     *
     * @since 1.0.0
     */
    BROKEN("2"),
    /**
     * 遺失
     *
     * @since 1.0.0
     */
    LOSE("3");

    /**
     * 狀態編號
     *
     * @since 1.0.0
     */
    public final String status;

    RentalDetailStatus(String status) {
        this.status = status;
    }
}
