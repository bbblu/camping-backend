package tw.edu.ntub.imd.camping.databaseconfig.entity.view;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Immutable;
import tw.edu.ntub.birc.common.wrapper.date.DateTimeWrapper;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.converter.DateTimeWrapperConverter;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProductGroup;

import javax.persistence.*;

/**
 * 可租借商品列表
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = "productGroupById")
@Entity
@Table(name = "can_borrow_product_group", schema = Config.DATABASE_NAME)
@Immutable
public class CanBorrowProductGroup {
    /**
     * 商品群組編號
     *
     * @since 1.0.0
     */
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer id;

    /**
     * 商品群組封面圖
     *
     * @since 1.0.0
     */
    @Column(name = "cover_image", nullable = false)
    private String coverImage;

    /**
     * 租借價格
     *
     * @since 1.0.0
     */
    @Column(name = "price", nullable = false)
    private Integer price;

    /**
     * 可租借的起始時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "borrow_start_date", nullable = false)
    private DateTimeWrapper borrowStartDate;

    /**
     * 可租借的結束時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "borrow_end_date", nullable = false)
    private DateTimeWrapper borrowEndDate;

    /**
     * 使用者名稱，格式為：暱稱(帳號)
     *
     * @since 1.0.0
     */
    @Column(name = "user_name", length = 152, nullable = false)
    private String userName;

    /**
     * 商品群組
     *
     * @see ProductGroup
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, columnDefinition = "UNSIGNED", insertable = false, updatable = false)
    private ProductGroup productGroupById;
}
