package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 通知類型
 * <ul>
 *     <li>0: 物品已出租</li>
 *     <li>1: 付款成功</li>
 *     <li>2: 付款失敗</li>
 *     <li>3: 租借取消</li>
 *     <li>4: 出租取消</li>
 *     <li>5: 物品已送達</li>
 *     <li>6: 物品已歸還</li>
 *     <li>7: 交易完成</li>
 *     <li>8: 對方已評價</li>
 * </ul>
 *
 * @since 1.7.0
 */
public enum NotificationType {
    RENTAL("您的商品有人提出租借，請至租借紀錄查看"),
    PAYMENT_SUCCESS("您的交易(編號%d)的付款已通過!"),
    PAYMENT_FAIL("您的交易(編號%d)的付款失敗，詳細原因請詢問辦卡銀行。"),
    CANCEL_RENTER("您租借的交易編號%d已取消，請至租借紀錄查看"),
    CANCEL_PRODUCT_OWNER("您出借的交易編號%d已取消，請至租借紀錄查看"),
    PRODUCT_SERVICE("您租借的物品(交易編號%d)已送達倉庫(地址)，請在%tm月%td號%tH點前至倉庫領取"),
    PRODUCT_RETURNED("您出借的物品(交易編號%d)使用者已歸還至倉庫，請在%tm月%td號%tH點前至倉庫領回"),
    COMMENT("交易編號%d已完成，您可以至租借紀錄評價對方"),
    COMMENT_INVITE("交易編號%d的租借/出借者已對此次交易進行評價，您可以至租借紀錄評價對方");

    private final String template;

    NotificationType(String template) {
        this.template = template;
    }

    public String getMessage(Object... args) {
        return String.format(template, args);
    }
}
