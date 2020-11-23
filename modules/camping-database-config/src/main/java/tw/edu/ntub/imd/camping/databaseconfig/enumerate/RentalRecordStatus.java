package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 租借紀錄狀態
 *
 * <ul>
 *     <li>00 已取消</li>
 *     <li>01 被退貨</li>
 *     <li>02 已終止待領回</li>
 *     <li>03 已終止且已領回</li>
 *     <li>04 未同意</li>
 *     <li>05 已同意待付款</li>
 *     <li>06 已付款未寄放</li>
 *     <li>07 已寄放未取貨</li>
 *     <li>08 已取貨未歸還</li>
 *     <li>01 已歸還未取回</li>
 *     <li>10 已取回未評價</li>
 *     <li>11 已評價</li>
 * </ul>
 *
 * @since 1.0.0
 */
public enum RentalRecordStatus {
    /**
     * 已取消
     *
     * @since 1.0.0
     */
    ALREADY_CANCEL,
    /**
     * 被退貨
     *
     * @since 1.7.0
     */
    BE_RETURNED,
    /**
     * 已終止待領回
     *
     * @since 1.7.0
     */
    TERMINATE,
    /**
     * 已終止且已領回
     *
     * @since 1.7.0
     */
    ALREADY_TERMINATED,
    /**
     * 未同意
     *
     * @since 1.6.1
     */
    NOT_AGREE,
    /**
     * 已同意待付款
     *
     * @since 1.6.1
     */
    NOT_PAY,
    /**
     * 已付款未寄放
     *
     * @since 1.6.0
     */
    NOT_PLACE,
    /**
     * 已寄放未取貨
     *
     * @since 1.0.0
     */
    NOT_PICK_UP,
    /**
     * 已取貨未歸還
     *
     * @since 1.0.0
     */
    NOT_RETURN,
    /**
     * 已歸還未取回
     *
     * @since 1.0.0
     */
    NOT_RETRIEVE,
    /**
     * 已取回未評價
     *
     * @since 1.6.0
     */
    NOT_COMMENT,
    /**
     * 已評價
     *
     * @since 1.6.1
     */
    ALREADY_COMMENT;

    @Override
    @JsonValue
    public String toString() {
        switch (this) {
            case ALREADY_CANCEL:
                return "已取消";
            case BE_RETURNED:
                return "已退貨";
            case TERMINATE:
                return "已終止";
            case ALREADY_TERMINATED:
                return "已取回";
            case NOT_AGREE:
                return "未同意";
            case NOT_PAY:
                return "待付款";
            case NOT_PLACE:
                return "未寄放";
            case NOT_PICK_UP:
                return "未領取";
            case NOT_RETURN:
                return "未歸還";
            case NOT_RETRIEVE:
                return "未取回";
            case NOT_COMMENT:
                return "未評價";
            case ALREADY_COMMENT:
                return "已評價";
            default:
                return name();
        }
    }
}
