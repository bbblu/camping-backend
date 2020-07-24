package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.camping.databaseconfig.Config;

import javax.persistence.*;

/**
 * 使用者權限
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "user_role", schema = Config.DATABASE_NAME)
public class UserRole {
    /**
     * 權限編號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "UNSIGNED")
    private Integer id;

    /**
     * 權限名稱
     *
     * @since 1.0.0
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
