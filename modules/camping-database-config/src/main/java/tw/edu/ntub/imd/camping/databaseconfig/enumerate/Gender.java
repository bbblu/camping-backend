package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 使用者性別(0: 男/ 1: 女/ 2: 未提供)
 *
 * @see tw.edu.ntub.imd.camping.databaseconfig.entity.User
 * @since 1.0.0
 */
public enum Gender {
    /**
     * 男性
     *
     * @since 1.0.0
     */
    MALE("0", "男"),
    /**
     * 女性
     *
     * @since 1.0.0
     */
    FEMALE("1", "女"),
    /**
     * 未提供
     *
     * @since 1.0.0
     */
    UNKNOWN("2", "未提供");

    /**
     * 性別編號
     *
     * @since 1.0.0
     */
    public final String id;
    /**
     * 性別
     *
     * @since 1.0.0
     */
    public final String name;

    Gender(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Gender getById(String id) {
        for (Gender gender : values()) {
            if (gender.id.equals(id)) {
                return gender;
            }
        }
        throw new IllegalStateException("沒有此性別編號：" + id);
    }
}
