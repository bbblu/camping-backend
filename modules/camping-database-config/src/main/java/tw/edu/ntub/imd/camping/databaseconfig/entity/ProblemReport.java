package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProblemReportListener;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 問題回報
 *
 * @since 1.4.0
 */
@Data
@Entity
@EntityListeners(ProblemReportListener.class)
@Table(name = "problem_report", schema = Config.DATABASE_NAME)
public class ProblemReport {
    /**
     * 流水編號
     *
     * @since 1.4.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 問題類型
     *
     * @since 1.4.9
     */
    @Enumerated
    @Column(name = "type", length = 1, nullable = false)
    private ProblemReportType type;

    /**
     * 狀態(0: 未處理/ 1: 處理中/ 2: 已完成)
     *
     * @since 1.4.0
     */
    @Enumerated
    @Column(name = "status", length = 1, nullable = false)
    private ProblemReportStatus status;

    /**
     * 回報者信箱
     *
     * @since 1.4.10
     */
    @Column(name = "reporter_email", nullable = false)
    private String reporterEmail;

    /**
     * 回報日期
     *
     * @since 1.4.0
     */
    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    /**
     * 回報內容
     *
     * @since 1.4.0
     */
    @Column(name = "report_content", nullable = false, columnDefinition = "TEXT")
    private String reportContent;

    /**
     * 處理人帳號
     *
     * @since 1.4.0
     */
    @Column(name = "handler", length = 100)
    private String handler;

    /**
     * 處理時間
     *
     * @since 1.5.4
     */
    @Column(name = "handle_date")
    private LocalDateTime handleDate;

    /**
     * 處理結果
     *
     * @since 1.4.0
     */
    @Column(name = "handle_result", columnDefinition = "TEXT")
    private String handleResult;

    /**
     * 處理完成時間
     *
     * @since 1.5.4
     */
    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    /**
     * 最後修改人帳號
     *
     * @since 1.4.0
     */
    @Column(name = "last_modify_account", length = 100, nullable = false)
    private String lastModifyAccount;

    /**
     * 最後修改日期
     *
     * @since 1.4.0
     */
    @Column(name = "last_modify_date", nullable = false)
    private LocalDateTime lastModifyDate;

    /**
     * 處理人
     *
     * @see User
     * @since 1.4.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handler", referencedColumnName = "account", insertable = false, updatable = false)
    private User userByHandler;

    /**
     * 最後修改人
     *
     * @see User
     * @since 1.4.0
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modify_account", referencedColumnName = "account", insertable = false, updatable = false)
    private User userByModifyLastModifyAccount;
}
