package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.entity.listener.ProblemReportListener;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 問題回報
 *
 * @since 1.4.0
 */
@Data
@EqualsAndHashCode(exclude = {"userByHandler", "userByModifyId"})
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
     * 狀態(0: 未處理/ 1: 處理中/ 2: 已完成)
     *
     * @since 1.4.0
     */
    @Enumerated
    @Column(name = "status", length = 1, nullable = false)
    private ProblemReportStatus status;

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
     * 處理結果
     *
     * @since 1.4.0
     */
    @Column(name = "handle_result", nullable = false, columnDefinition = "TEXT")
    private String handleResult;

    /**
     * 最後修改人帳號
     *
     * @since 1.4.0
     */
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;

    /**
     * 最後修改日期
     *
     * @since 1.4.0
     */
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate;

    /**
     * 處理人
     *
     * @see User
     * @since 1.4.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handler", referencedColumnName = "account", insertable = false, updatable = false)
    private User userByHandler;

    /**
     * 最後修改人
     *
     * @see User
     * @since 1.4.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modify_id", referencedColumnName = "account", insertable = false, updatable = false)
    private User userByModifyId;
}
