package tw.edu.ntub.imd.camping.service.impl;

import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.camping.bean.ProblemReportBean;
import tw.edu.ntub.imd.camping.config.util.SecurityUtils;
import tw.edu.ntub.imd.camping.databaseconfig.dao.ProblemReportDAO;
import tw.edu.ntub.imd.camping.databaseconfig.entity.ProblemReport;
import tw.edu.ntub.imd.camping.databaseconfig.enumerate.ProblemReportStatus;
import tw.edu.ntub.imd.camping.dto.Mail;
import tw.edu.ntub.imd.camping.exception.NeverHandleProblemReportException;
import tw.edu.ntub.imd.camping.exception.NotFoundException;
import tw.edu.ntub.imd.camping.exception.ProblemReportHandlerMismatchException;
import tw.edu.ntub.imd.camping.service.ProblemReportService;
import tw.edu.ntub.imd.camping.service.transformer.ProblemReportTransformer;
import tw.edu.ntub.imd.camping.util.MailSender;

import java.time.format.DateTimeFormatter;

@Service
public class ProblemReportServiceImpl extends BaseServiceImpl<ProblemReportBean, ProblemReport, Integer> implements ProblemReportService {
    private final ProblemReportDAO problemReportDAO;
    private final ProblemReportTransformer transformer;
    private final MailSender mailSender;

    public ProblemReportServiceImpl(
            ProblemReportDAO problemReportDAO,
            ProblemReportTransformer transformer,
            MailSender mailSender) {
        super(problemReportDAO, transformer);
        this.problemReportDAO = problemReportDAO;
        this.transformer = transformer;
        this.mailSender = mailSender;
    }

    @Override
    public ProblemReportBean save(ProblemReportBean problemReportBean) {
        ProblemReport problemReport = transformer.transferToEntity(problemReportBean);
        ProblemReport saveResult = problemReportDAO.saveAndFlush(problemReport);

        Mail mail = new Mail("/mail/problem_report/create_response");
        mail.addSendTo(problemReport.getReporterEmail());
        mail.setSubject("我們已收到您的回報");
        mail.addAttribute("reporterEmail", problemReport.getReporterEmail());
        mailSender.sendMail(mail);

        return transformer.transferToBean(saveResult);
    }

    @Override
    public void updateHandler(int id) {
        ProblemReport problemReport = problemReportDAO.findById(id).orElseThrow(() -> new NotFoundException("找不到此問題回報：" + id));
        problemReport.setStatus(ProblemReportStatus.PROCESSED);
        problemReport.setHandler(SecurityUtils.getLoginUserAccount());
        problemReportDAO.update(problemReport);
    }

    @Override
    public void updateHandleResult(int id, String handleResult) {
        ProblemReport problemReport = problemReportDAO.findById(id).orElseThrow(() -> new NotFoundException("找不到此問題回報：" + id));
        if (problemReport.getStatus() != ProblemReportStatus.PROCESSED) {
            throw new NeverHandleProblemReportException(id);
        } else if (StringUtils.isNotEquals(problemReport.getHandler(), SecurityUtils.getLoginUserAccount())) {
            throw new ProblemReportHandlerMismatchException();
        }

        problemReport.setStatus(ProblemReportStatus.COMPLETE);
        problemReport.setHandleResult(handleResult);
        problemReportDAO.update(problemReport);

        Mail mail = new Mail("/mail/problem_report/handle_response");
        mail.addSendTo(problemReport.getReporterEmail());
        mail.setSubject("關於您在" + problemReport.getReportDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + "提出的問題");
        mail.addAttribute("problemReport", problemReport);
        mail.addAttribute("handler", problemReport.getUserByHandler());
        mailSender.sendMail(mail);
    }
}
