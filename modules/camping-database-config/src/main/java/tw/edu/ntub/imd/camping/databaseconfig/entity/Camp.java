package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;

/**
 * 可租借城市
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = "city")
@Entity
@Table(name = "camp", schema = Config.DATABASE_NAME)
public class Camp {
    /**
     * 流水編號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 露營區名稱
     *
     * @since 1.0.0
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * 城市名稱，如臺北市、宜蘭縣
     *
     * @since 1.0.0
     */
    @Column(name = "city_name", length = 20, nullable = false)
    private String cityName;

    /**
     * 區名稱，如中正區、宜蘭市
     *
     * @since 1.0.0
     */
    @Column(name = "city_area_name", length = 20, nullable = false)
    private String cityAreaName;

    /**
     * 經度
     *
     * @since 1.0.0
     */
    @Column(name = "longitude", length = 15, nullable = false)
    private String longitude;

    /**
     * 緯度
     *
     * @since 1.0.0
     */
    @Column(name = "latitude", length = 15, nullable = false)
    private String latitude;

    /**
     * 可租借城市
     *
     * @see City
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "city_name", referencedColumnName = "name", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "city_area_name", referencedColumnName = "area_name", nullable = false, insertable = false, updatable = false)
    })
    private City city;
}
