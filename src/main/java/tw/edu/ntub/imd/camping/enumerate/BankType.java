package tw.edu.ntub.imd.camping.enumerate;

/**
 * 金融機構類型(0: 銀行/ 1: 信合社/ 2: 農會/ 3: 漁會/ 4: 郵局)
 *
 * @since 1.4.0
 */
public enum BankType {
    BANK("銀行"),
    LETTER_COOPERATIVE("信合社"),
    PEASANT_ASSOCIATION("農會"),
    FISHERMEN("漁會"),
    POST_OFFICE("郵局");

    public final String name;

    BankType(String name) {
        this.name = name;
    }
}
