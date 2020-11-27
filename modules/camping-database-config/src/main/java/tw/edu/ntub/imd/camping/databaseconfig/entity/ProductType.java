package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.*;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProductTypeListener;

import javax.persistence.*;

/**
 * 商品細項類型對照表
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@EntityListeners(ProductTypeListener.class)
@Table(name = "product_type", schema = Config.DATABASE_NAME)
public class ProductType {
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
     * 類型名稱
     *
     * @since 1.0.0
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    @Getter(AccessLevel.NONE)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    public ProductType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 是否啟用(0: 否/ 1: 是)
     *
     * @since 1.0.0
     */
    public Boolean isEnable() {
        return enable;
    }
}
