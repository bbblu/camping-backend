package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 使用者不良紀錄類型(0: 取消交易/ 1: 退貨/ 2: 終止交易/ 3: 毀損商品)
 *
 * @since 1.7.0
 */
public enum UserBadRecordType {
    /**
     * 取消交易
     *
     * @since 1.7.0
     */
    CANCEL_RECORD,
    /**
     * 退貨
     *
     * @since 1.7.0
     */
    BE_RETURN,
    /**
     * 終止交易
     *
     * @since 1.7.0
     */
    TERMINATE_RECORD,
    /**
     * 毀損商品
     *
     * @since 1.7.0
     */
    BROKEN_PRODUCT;


    @Override
    public String toString() {
        switch (this) {
            case CANCEL_RECORD:
                return "取消交易";
            case BE_RETURN:
                return "退貨";
            case TERMINATE_RECORD:
                return "終止交易";
            case BROKEN_PRODUCT:
                return "毀損商品";
            default:
                return name();
        }
    }
}
