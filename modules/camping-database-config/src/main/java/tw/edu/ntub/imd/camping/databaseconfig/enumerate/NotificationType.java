package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

public enum NotificationType {
    RENTAL("您的商品有人提出租借，請至租借紀錄查看"),
    PAYMENT_SUCCESS("您的交易(編號%d)的付款已通過!"),
    PAYMENT_FAIL("您的交易(編號%d)的付款失敗，詳細原因請詢問辦卡銀行。"),
    CANCEL_SUCCESS("您對交易編號%d的取消申請已通過，請至租借紀錄查看"),
    CANCEL_FAIL("您對交易編號%d的取消申請失敗，請至租借紀錄查看，若有需要可使用問題回報進行詢問"),
    PRODUCT_SERVICE("您租借的物品(交易編號%d)已送達倉庫(地址)，請在%tm月%td號%tH點前至倉庫領取"),
    PRODUCT_RETURNED("您出借的物品(交易編號%d)使用者已歸還至倉庫，請在%tm月%td號%tH點前至倉庫領回"),
    COMMENT_INVITE("交易編號%d的租借/出借者已對此次交易進行評價，您可以至租借紀錄評價對方");

    private final String template;

    NotificationType(String template) {
        this.template = template;
    }

    public String getMessage(Object... args) {
        return String.format(template, args);
    }
}
