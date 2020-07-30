package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.birc.common.wrapper.date.DateTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.LocalDateTimeWrapper;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.converter.DateTimeWrapperConverter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.RentalDetailStatusConverter;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalDetailStatus;

import javax.persistence.*;

/**
 * 租借詳細記錄
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = {
        "rentalRecordByRecordId",
        "productByProductId",
        "userByLastModifyAccount"
})
@Entity
@Table(name = "rental_detail", schema = Config.DATABASE_NAME)
public class RentalDetail {
    /**
     * 流水編號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer id;

    /**
     * 租借紀錄編號
     *
     * @since 1.0.0
     */
    @Column(name = "record_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer recordId;

    /**
     * 商品編號
     *
     * @since 1.0.0
     */
    @Column(name = "product_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer productId;

    /**
     * 狀態(0: 未歸還/ 1: 已歸還/ 2: 損壞/ 3: 遺失)
     *
     * @since 1.0.0
     */
    @Convert(converter = RentalDetailStatusConverter.class)
    @Column(name = "status", length = 1, nullable = false)
    private RentalDetailStatus status;

    /**
     * 出借方檢查備註
     *
     * @since 1.0.0
     */
    @Column(name = "check_memo", length = 500)
    private String checkMemo;

    /**
     * 最後修改者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後修改時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "last_modify_date", nullable = false)
    private DateTimeWrapper lastModifyDate = new LocalDateTimeWrapper();

    /**
     * 租借紀錄
     *
     * @see RentalRecord
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private RentalRecord rentalRecordByRecordId;

    /**
     * 商品
     *
     * @see Product
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private Product productByProductId;

    /**
     * 最後修改者
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modify_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByLastModifyAccount;
}
