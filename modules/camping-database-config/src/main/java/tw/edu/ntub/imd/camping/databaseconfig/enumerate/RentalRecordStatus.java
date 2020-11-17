package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 租借紀錄狀態(0: 申請取消/ 1: 已取消/ 2: 被退貨/ 3: 被求償/ 4: 等待接受租借/ 5: 待付款/ 6: 未寄放/ 7: 未取貨/ 8: 未歸還/ 9: 未取回/ 10: 未評價/ 11: 已評價)
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
     * 等待接受租借
     *
     * @since 1.6.1
     */
    WAITING_FOR_CONSENT,
    /**
     * 待付款
     *
     * @since 1.6.1
     */
    PENDING_PAYMENT,
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
    NOT_COMMENT,
    /**
     * 已評價
     *
     * @since 1.6.1
     */
    COMMENTED;

    public RentalRecordStatus next() {
        switch (this) {
            case WAITING_FOR_CONSENT:
                return PENDING_PAYMENT;
            case PENDING_PAYMENT:
                return NOT_PLACED;
            case NOT_PLACED:
                return NOT_PICK_UP;
            case NOT_PICK_UP:
                return NOT_RETURN;
            case NOT_RETURN:
                return NOT_RETRIEVE;
            case NOT_RETRIEVE:
                return NOT_COMMENT;
            case NOT_COMMENT:
                return COMMENTED;
            case APPLY_CANCEL:
            case CANCEL:
            case BE_RETURNED:
            case BE_CLAIM:
            case COMMENTED:
            default:
                return null;
        }
    }

    public boolean isCanChangeTo(RentalRecordStatus status) {
        switch (this) {
            case WAITING_FOR_CONSENT:
                return status == PENDING_PAYMENT || status == APPLY_CANCEL || status == CANCEL;
            case PENDING_PAYMENT:
                return status == NOT_PLACED || status == APPLY_CANCEL || status == CANCEL;
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
            case NOT_COMMENT:
                return status == COMMENTED;
            case CANCEL:
            case BE_RETURNED:
            case BE_CLAIM:
            case COMMENTED:
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
            case WAITING_FOR_CONSENT:
                return "等待接受租借";
            case PENDING_PAYMENT:
                return "待付款";
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
            case COMMENTED:
                return "已平價";
            default:
                return name();
        }
    }
}
