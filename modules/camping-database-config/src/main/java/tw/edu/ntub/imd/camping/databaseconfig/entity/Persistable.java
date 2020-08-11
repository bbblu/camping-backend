package tw.edu.ntub.imd.camping.databaseconfig.entity;

import java.io.Serializable;

public interface Persistable<ID extends Serializable> extends org.springframework.data.domain.Persistable<ID> {
    void setSave(boolean isSave);
}
