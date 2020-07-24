package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 可租借城市
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "city", schema = Config.DATABASE_NAME)
@IdClass(City.class)
public class City implements Serializable {
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
    private String area_name;
}
