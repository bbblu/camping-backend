package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.imd.camping.databaseconfig.Config;
import tw.edu.ntub.imd.camping.databaseconfig.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.LogRecordDeviceConverter;
import tw.edu.ntub.imd.camping.databaseconfig.converter.LogRecordDeviceTypeConverter;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.LogRecordDevice;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.LogRecordDeviceType;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 使用者操作紀錄
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(exclude = "userByExecutor")
@Entity
@Table(name = "log_record", schema = Config.DATABASE_NAME)
public class LogRecord {
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
     * 伺服器版本
     *
     * @since 1.0.0
     */
    @Column(name = "server_version", length = 20, nullable = false)
    private String serverVersion;

    /**
     * 使用者IPv4
     *
     * @since 1.0.0
     */
    @Column(name = "ip", length = 19, nullable = false)
    private String ip;

    /**
     * 發送請求時的方法
     *
     * @since 1.0.0
     */
    @Column(name = "method", length = 6, nullable = false)
    private String method;

    /**
     * 網址
     *
     * @since 1.0.0
     */
    @Column(name = "url", length = 500, nullable = false)
    private String url;

    /**
     * 使用者帳號
     *
     * @since 1.0.0
     */
    @Column(name = "executor", length = 100, nullable = false)
    private String executor;

    /**
     * 操作日期
     *
     * @since 1.0.0
     */
    @Column(name = "execute_date", nullable = false)
    private LocalDateTime executeDate = LocalDateTime.now();

    /**
     * 使用設備(-1: Unknown/ 00: Postman/ 01: 瀏覽器/ 02: App瀏覽器/ 03: App)
     *
     * @since 1.0.0
     */
    @Column(name = "device", length = 2, nullable = false)
    @Convert(converter = LogRecordDeviceConverter.class)
    private LogRecordDevice device;

    /**
     * 設備類型(-1: Unknown/ 00: Postman/ 01: IE/ 02: Edge/ 03: Chrome/ 04:FireFox/ 05: Safari/ 06: Opera/ 07: Android/ 08: iOS)
     *
     * @since 1.0.0
     */
    @Column(name = "device_type", length = 2, nullable = false)
    @Convert(converter = LogRecordDeviceTypeConverter.class)
    private LogRecordDeviceType deviceType;

    /**
     * 設備版本
     *
     * @since 1.0.0
     */
    @Column(name = "device_version", length = 100, nullable = false)
    private String deviceVersion;

    /**
     * 執行結果
     *
     * @since 1.0.0
     */
    @Column(name = "result", nullable = false)
    @Convert(converter = BooleanTo1And0Converter.class)
    @Getter(AccessLevel.NONE)
    private Boolean success;

    /**
     * 錯誤代碼
     *
     * @since 1.0.0
     */
    @Column(name = "error_code", length = 50, nullable = false)
    private String errorCode;

    /**
     * 回應訊息
     *
     * @since 1.0.0
     */
    @Column(name = "message", length = 3000, nullable = false)
    private String message;

    /**
     * 使用者
     *
     * @see User
     * @since 1.0.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByExecutor;

    public void setMethod(String method) {
        if (method != null) {
            this.method = method.toUpperCase();
        }
    }

    public Boolean isSuccess() {
        return success;
    }
}
