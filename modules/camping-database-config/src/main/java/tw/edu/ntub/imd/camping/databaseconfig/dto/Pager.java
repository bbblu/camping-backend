package tw.edu.ntub.imd.camping.databaseconfig.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Pager {
    private final boolean infinity;
    private final int page;
    private final int count;

    public static Pager getInstance(int page, int count) {
        return new Pager(false, page, count);
    }

    public static Pager infinity(int page, int count) {
        return new Pager(true, page, count);
    }

    public int getFirstResultIndex() {
        return (page - 1) * count;
    }
}
