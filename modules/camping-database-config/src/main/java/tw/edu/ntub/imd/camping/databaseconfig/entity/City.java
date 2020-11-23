package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;

/**
 * 可租借城市
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "city", schema = Config.DATABASE_NAME)
public class City {
    /**
     * 流水編號
     *
     * @since 1.6.2
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    /**
     * 城市名稱，如台北市、宜蘭縣
     *
     * @since 1.0.0
     */
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * 區名稱，如中正區、宜蘭市
     *
     * @since 1.0.0
     */
    @Column(name = "area_name", length = 20, nullable = false)
    private String areaName;

    /**
     * 是否啟用(0: 不啟用/ 1: 啟用)
     *
     * @since 1.3.3
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    /**
     * 是否啟用(0: 不啟用/ 1: 啟用)
     *
     * @since 1.3.3
     */
    public Boolean isEnable() {
        return enable;
    }
}
