package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 取消狀態(0: 等待買方同意/ 1: 等待出借方同意/ 2: 取消被拒絕/ 3: 已取消)
 *
 * @since 1.5.0
 */
public enum RentalRecordCancelStatus {
    /**
     * 等待買方同意
     *
     * @since 1.5.0
     */
    WAIT_RENTER_AGREE,
    /**
     * 等待出借方同意
     *
     * @since 1.5.0
     */
    WAIT_PRODUCT_OWNER_AGREE,
    /**
     * 取消被拒絕
     *
     * @since 1.5.0
     */
    CANCEL_DENIED,
    /**
     * 已取消
     *
     * @since 1.5.0
     */
    CANCEL_SUCCESS
}
