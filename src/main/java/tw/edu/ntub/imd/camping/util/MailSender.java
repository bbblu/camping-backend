package tw.edu.ntub.imd.camping.util;

import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tw.edu.ntub.birc.common.exception.UnknownException;
import tw.edu.ntub.imd.camping.dto.Mail;

import javax.mail.MessagingException;

@AllArgsConstructor
@Component("customMailSender")
public class MailSender {
    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;

    public void sendMail(Mail mail) {
        try {
            Context context = new Context();
            context.setVariables(mail.getModelMap());
            String processResult = templateEngine.process(mail.getTemplateViewName(), context);
            MimeMessageHelper message = new MimeMessageHelper(
                    javaMailSender.createMimeMessage(),
                    false,
                    "UTF-8"
            );
            message.setFrom("借借露<a0909002250@gmail.com>");
            message.setSubject(mail.getSubject());
            message.setTo(mail.getTo().toArray(String[]::new));
            message.setText(processResult, true);
            javaMailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new UnknownException(e);
        }
    }
}
