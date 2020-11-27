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
public class RecommendProductPriceId implements Serializable {
    /**
     * 商品類型
     *
     * @since 1.8.0
     */
    @Id
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 商品子類型
     *
     * @since 1.8.0
     */
    @Id
    @Column(name = "sub_type", nullable = false)
    private Integer subType;
}
