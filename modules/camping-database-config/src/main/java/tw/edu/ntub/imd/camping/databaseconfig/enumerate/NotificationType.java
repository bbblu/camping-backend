package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 通知類型
 * <ul>
 * <li>0: 租借</li>
 * <li>1: 租借成功</li>
 * <li>2: 租借遭拒</li>
 * <li>3: 付款成功</li>
 * <li>4: 付款失敗</li>
 * <li>5: 對方付款</li>
 * <li>6: 取消成功</li>
 * <li>7: 對方取消</li>
 * <li>8: 物品退貨</li>
 * <li>9: 物品送達</li>
 * <li>10: 物品歸還</li>
 * <li>11: 評價邀請</li>
 * </ul>
 *
 * @since 1.7.0
 */
public enum NotificationType {
    /**
     * 租借，不需要參數
     *
     * @since 1.7.0
     */
    RENTAL("您的商品有人提出租借，請至租借紀錄查看。"),
    /**
     * 租借成功，需要商品名稱
     *
     * @since 1.8.8
     */
    RENTAL_AGREE("您在(%s)中提出的租借要求對方已同意，請至租借紀錄進行下一個動作"),
    /**
     * 租借遭拒，需要商品名稱
     *
     * @since 1.8.8
     */
    RENTAL_DENIED("您在(%s)中提出的租借要求被拒"),
    /**
     * 付款成功，需要交易編號
     *
     * @since 1.7.0
     */
    PAYMENT_SUCCESS("您的交易(編號%d)的付款已通過!"),
    /**
     * 付款失敗，需要交易編號
     *
     * @since 1.7.0
     */
    PAYMENT_FAIL("您的交易(編號%d)的付款失敗，詳細原因請詢問辦卡銀行。"),
    /**
     * 對方付款，需要交易編號、租借日前一天 x 2
     *
     * @since 1.8.8
     */
    ALREADY_PAY("您的交易(編號%d)對方已付款，請在(%tm月%td號)之前送至倉庫"),
    /**
     * 取消成功，需要交易編號
     *
     * @since 1.8.8
     */
    CANCEL_SUCCESS("您對(交易編號%d)的取消申請已通過，請至租借紀錄查看"),
    /**
     * 對方取消，需要交易編號
     *
     * @since 1.8.8
     */
    ALREADY_CANCEL("您的交易(編號%d)已被對方取消"),
    /**
     * 物品退貨，需要交易編號、取回天數
     *
     * @since 1.8.8
     */
    BE_RETURN("您的交易(編號%d)商品已被退貨，請在%d天內至倉庫領取"),
    /**
     * 物品送達，需要交易編號、租借結束日期x3
     *
     * @since 1.8.8
     */
    PLACED("您租借的物品(交易編號%d)已送達倉庫，請在%tm月%td號%tH點前至倉庫領取"),
    /**
     * 物品歸還，需要交易編號、歸還日三天後x3
     *
     * @since 1.8.8
     */
    ALREADY_RETURN("您出借的物品(交易編號%d)使用者已歸還至倉庫，請在%tm月%td號%tH點前至倉庫領回"),
    /**
     * 租借者評價，需要交易編號
     *
     * @since 1.8.8
     */
    RENTER_ALREADY_COMMENT("交易編號%d的租借者已對此次交易進行評價，您可以至租借紀錄評價對方"),
    /**
     * 出借者評價，需要交易編號
     *
     * @since 1.8.8
     */
    PRODUCT_OWNER_ALREADY_COMMENT("交易編號%d的出借者已對此次交易進行評價，您可以至租借紀錄評價對方");

    private final String template;

    NotificationType(String template) {
        this.template = template;
    }

    public String getMessage(Object... args) {
        return String.format(template, args);
    }
}
