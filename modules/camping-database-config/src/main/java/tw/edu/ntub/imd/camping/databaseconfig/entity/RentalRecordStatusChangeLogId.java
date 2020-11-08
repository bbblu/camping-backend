package tw.edu.ntub.imd.camping.databaseconfig.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.RentalRecordStatus;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 租借紀錄狀態變更紀錄ID
 *
 * @since 1.6.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRecordStatusChangeLogId implements Serializable {
    /**
     * 租借紀錄編號
     *
     * @since 1.6.0
     */
    @Id
    @Column(name = "record_id", nullable = false)
    private Integer recordId;

    /**
     * 變更後的狀態
     *
     * @since 1.6.0
     */
    @Id
    @Column(name = "to_status", length = 1, nullable = false)
    private RentalRecordStatus toStatus;
}
