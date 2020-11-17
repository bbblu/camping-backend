package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 租借紀錄狀態(0: 申請取消/ 1: 已取消/ 2: 被退貨/ 3: 被求償/ 4: 未寄放/ 5: 未取貨/ 6: 未歸還/ 7: 未取回/ 8: 未評價)
 *
 * @since 1.0.0
 */
public enum RentalRecordStatus {
    /**
     * 申請取消
     *
     * @since 1.6.0
     */
    APPLY_CANCEL,
    /**
     * 已取消
     *
     * @since 1.0.0
     */
    CANCEL,
    /**
     * 被退貨
     *
     * @since 1.6.0
     */
    BE_RETURNED,
    /**
     * 被求償
     *
     * @since 1.6.0
     */
    BE_CLAIM,
    /**
     * 未寄放
     *
     * @since 1.6.0
     */
    NOT_PLACED,
    /**
     * 未取貨
     *
     * @since 1.0.0
     */
    NOT_PICK_UP,
    /**
     * 未歸還
     *
     * @since 1.0.0
     */
    NOT_RETURN,
    /**
     * 未取回
     *
     * @since 1.0.0
     */
    NOT_RETRIEVE,
    /**
     * 未評價
     *
     * @since 1.6.0
     */
    NOT_COMMENT;

    public RentalRecordStatus next() {
        switch (this) {
            case NOT_PLACED:
                return NOT_PICK_UP;
            case NOT_PICK_UP:
                return NOT_RETURN;
            case NOT_RETURN:
                return NOT_RETRIEVE;
            case NOT_RETRIEVE:
                return NOT_COMMENT;
            case APPLY_CANCEL:
            case CANCEL:
            case BE_RETURNED:
            case BE_CLAIM:
            case NOT_COMMENT:
            default:
                return null;
        }
    }

    public boolean isCanChangeTo(RentalRecordStatus status) {
        switch (this) {
            case NOT_PLACED:
                return status == NOT_PICK_UP || status == APPLY_CANCEL || status == CANCEL;
            case NOT_PICK_UP:
                return status == NOT_RETURN || status == CANCEL;
            case NOT_RETURN:
                return status == NOT_RETRIEVE || status == BE_RETURNED;
            case NOT_RETRIEVE:
                return status == NOT_COMMENT || status == BE_CLAIM;
            case APPLY_CANCEL:
                return status == CANCEL;
            case CANCEL:
            case BE_RETURNED:
            case BE_CLAIM:
            case NOT_COMMENT:
            default:
                return false;
        }
    }


    @Override
    @JsonValue
    public String toString() {
        switch (this) {
            case APPLY_CANCEL:
                return "申請取消";
            case CANCEL:
                return "已取消";
            case BE_RETURNED:
                return "被退貨";
            case BE_CLAIM:
                return "被求償";
            case NOT_PLACED:
                return "未寄放";
            case NOT_PICK_UP:
                return "未取貨";
            case NOT_RETURN:
                return "未歸還";
            case NOT_RETRIEVE:
                return "未取回";
            case NOT_COMMENT:
                return "未評價";
            default:
                return name();
        }
    }
}
