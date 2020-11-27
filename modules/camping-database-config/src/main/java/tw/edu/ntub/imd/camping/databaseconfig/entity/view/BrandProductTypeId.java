package tw.edu.ntub.imd.camping.databaseconfig.entity.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandProductTypeId implements Serializable {
    /**
     * 品牌
     *
     * @since 1.8.1
     */
    @Id
    @Column(name = "brand", nullable = false)
    private Integer brand;

    /**
     * 商品類型
     *
     * @since 1.8.1
     */
    @Id
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 類型名稱
     *
     * @since 1.8.1
     */
    @Column(name = "type_name", length = 50, nullable = false)
    private String typeName;
}
