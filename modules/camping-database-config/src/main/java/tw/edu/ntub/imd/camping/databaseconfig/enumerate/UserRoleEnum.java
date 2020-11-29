package tw.edu.ntub.imd.camping.databaseconfig.enumerate;

/**
 * 使用者權限列舉
 *
 * @since 1.0.0
 */
public enum UserRoleEnum {
    /**
     * 系統管理員，編號為{@literal 1}
     *
     * @since 1.0.0
     */
    ADMINISTRATOR(1, "Administrator"),
    /**
     * 管理者，編號為{@literal 2}
     *
     * @since 1.0.0
     */
    MANAGER(2, "Manager"),
    /**
     * 使用者，編號為{@literal 3}
     *
     * @since 1.0.0
     */
    USER(3, "User"),
    /**
     * 出借者，編號為{@literal 4}
     *
     * @since 1.0.0
     */
    RENTER(4, "Renter");

    /**
     * 權限編號
     *
     * @since 1.0.0
     */
    public final int id;
    /**
     * 權限名稱
     *
     * @since 1.0.0
     */
    public final String name;

    UserRoleEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserRoleEnum getByRoleId(int id) {
        for (UserRoleEnum value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        throw new IllegalStateException("無此對應的權限：" + id);
    }


    @Override
    public String toString() {
        switch (this) {
            case ADMINISTRATOR:
                return "最高權限";
            case MANAGER:
                return "管理員";
            case USER:
                return "一般使用者";
            case RENTER:
                return "出租者";
            default:
                return name;
        }
    }
}
