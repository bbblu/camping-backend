package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 露營經驗(0: 新手/ 1: 有過幾次經驗)
 *
 * @see tw.edu.ntub.imd.camping.databaseconfig.entity.User
 * @since 1.0.0
 */
public enum Experience {
    /**
     * 新手
     *
     * @since 1.0.0
     */
    ROOKIE("0"),
    /**
     * 老手
     *
     * @since 1.0.0
     */
    VETERAN("1");

    /**
     * 經驗編號
     *
     * @since 1.0.0
     */
    public final String id;

    Experience(String id) {
        this.id = id;
    }

    public static Experience getById(String id) {
        for (Experience value : values()) {
            if (value.id.equals(id)) {
                return value;
            }
        }
        throw new IllegalStateException("找不到對應的經驗：" + id);
    }

    @Override
    public String toString() {
        switch (this) {
            case ROOKIE:
                return "0~5次 新手";
            case VETERAN:
                return "5次以上 老手";
            default:
                return "不提供";
        }
    }
}
