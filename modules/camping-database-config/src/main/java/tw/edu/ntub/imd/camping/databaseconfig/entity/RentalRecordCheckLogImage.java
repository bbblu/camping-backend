package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;

/**
 * 租借紀錄商品檢查紀錄圖片
 *
 * @since 1.8.7
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "rental_record_check_log_image", schema = Config.DATABASE_NAME)
public class RentalRecordCheckLogImage {
    /**
     * 流水編號
     *
     * @since 1.8.7
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 檢查紀錄編號
     *
     * @since 1.8.7
     */
    @Column(name = "log_id", nullable = false)
    private Integer logId;


    /**
     * 圖片連結
     *
     * @since 1.8.7
     */
    @Column(name = "url", length = 512, nullable = false)
    private String url;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private RentalRecordCheckLog log;

    public RentalRecordCheckLogImage(String url) {
        this.url = url;
    }
}
