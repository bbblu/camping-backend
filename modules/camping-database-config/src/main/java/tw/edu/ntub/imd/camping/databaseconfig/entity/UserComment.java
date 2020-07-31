package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.birc.common.wrapper.date.DateTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.LocalDateTimeWrapper;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.converter.DateTimeWrapperConverter;

import javax.persistence.*;

/**
 * 使用者評價
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = {
        "userByUserAccount",
        "userByCommentAccount"
})
@Entity
@Table(name = "user_comment", schema = Config.DATABASE_NAME)
public class UserComment {
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
     * 被評價的使用者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "user_account", length = 100, nullable = false)
    private String userAccount;

    /**
     * 評價(0 ~ 5)
     *
     * @since 1.0.0
     */
    @Column(name = "comment", nullable = false, columnDefinition = "UNSIGNED")
    private Byte comment;

    /**
     * 評價者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "comment_account", length = 100, nullable = false)
    private String commentAccount;

    /**
     * 評價時間
     *
     * @since 1.0.0
     */
    @Convert(converter = DateTimeWrapperConverter.class)
    @Column(name = "comment_date", nullable = false)
    private DateTimeWrapper commentDate = new LocalDateTimeWrapper();

    /**
     * 被評價的使用者
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByUserAccount;

    /**
     * 評價者
     *
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_account", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByCommentAccount;
}
