package tw.edu.ntub.imd.camping.databaseconfig.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CityId implements Serializable {
    /**
     * 城市名稱，如臺北市、宜蘭縣
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * 區名稱，如中正區、宜蘭市
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "area_name", length = 20, nullable = false)
    private String areaName;
}
