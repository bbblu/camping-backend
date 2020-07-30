package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.birc.common.wrapper.date.DateTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.LocalDateTimeWrapper;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.DateTimeWrapperConverter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.RentalRecordStatusConverter;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.persistence.*;

/**
 * 租借紀錄
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = {
        "productGroupByGroupId",
        "userByRenterAccount",
        "contactInformationByRenterContactInformationId",
        "campByCampId",
        "userByLastModifyAccount"
})
@Entity
@Table(name = "rental_record", schema = Config.DATABASE_NAME)
public class RentalRecord {
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
     * 租借商品群組編號
     *
     * @since 1.0.0
     */
    @Column(name = "group_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer groupId;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Convert(converter = BooleanTo1And0Converter.class)
    @Column(name = "enable", nullable = false)
    private Boolean enable = true;

    /**
     * 狀態(0:取消/ 1: 未取貨/ 2:未歸還/ 3:已歸還/ 4: 已檢查)
     *
     * @see RentalRecordStatus
     * @since 1.0.0
     */
    @Convert(converter = RentalRecordStatusConverter.class)
    @Column(name = "status", length = 1, nullable = false)
    private RentalRecordStatus status = RentalRecordStatus.NOT_PICK_UP;

    /**
     * 租借者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "renter_account", length = 100, nullable = false)
    private String renterAccount;

    /**
     * 租借人的聯絡方式編號
     *
     * @since 1.0.0
     */
    @Column(name = "renter_contact_information_id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer renterContactInformationId;

    /**
     * 點選我要租借的時間，即建立時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "rental_date", nullable = false)
    private DateTimeWrapper rentalDate = new LocalDateTimeWrapper();

    /**
     * 預計租借起始時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "borrow_start_date", nullable = false)
    private DateTimeWrapper borrowStartDate;

    /**
     * 預計租借結束時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "borrow_end_date", nullable = false)
    private DateTimeWrapper borrowEndDate;

    /**
     * 露營區、露營地編號
     *
     * @since 1.0.0
     */
    @Column(name = "camp_id", columnDefinition = "UNSIGNED")
    private Integer campId;

    /**
     * 取貨時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "pick_date")
    private DateTimeWrapper pickDate;

    /**
     * 歸還時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "return_date")
    private DateTimeWrapper returnDate;

    /**
     * 檢查時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "check_date")
    private DateTimeWrapper checkDate;

    /**
     * 出借者檢查結果
     *
     * @since 1.0.0
     */
    @Column(name = "check_result", length = 1000)
    private String checkResult;

    /**
     * 取消時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "cancel_date")
    private DateTimeWrapper cancelDate;

    /**
     * 最後修改人帳號
     *
     * @since 1.0.0
     */
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後更新時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "last_modify_date", nullable = false)
    private DateTimeWrapper lastModifyDate = new LocalDateTimeWrapper();

    /**
     * 租借商品群組
     *
     * @see ProductGroup
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductGroup productGroupByGroupId;

    /**
     * 租借者
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByRenterAccount;

    /**
     * 租借人的聯絡方式
     *
     * @see ContactInformation
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_contact_information_id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ContactInformation contactInformationByRenterContactInformationId;

    /**
     * 露營區、露營地
     *
     * @see Camp
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", referencedColumnName = "id", columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private Camp campByCampId;

    /**
     * 最後修改人
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modify_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByLastModifyAccount;

    public Boolean isEnable() {
        return enable;
    }
}
