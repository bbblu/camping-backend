package tw.edu.ntub.imd.camping.bean;

import lombok.Data;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;

import java.time.LocalDateTime;

@Data
public class ProblemReportBean {
    private Integer id;
    private ProblemReportStatus status;
    private LocalDateTime reportDate;
    private String reportContent;
    private String handler;
    private String handleResult;
    private String modifyId;
    private LocalDateTime modifyDate;
}
