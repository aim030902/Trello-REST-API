package uz.aim.trellorestapi.services.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uz.aim.trellorestapi.dtos.user.UserDTO;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 12:06
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@Service
public class MailService {
    @Autowired
    private Configuration configuration;
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(UserDTO user, String activationLink) throws MessagingException, TemplateException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Activation link for AIM.uz");
        helper.setTo(user.getEmail());
        Map<String, Object> model = Map.of(
                "fullName", user.getFullName(),
                "activation_link", activationLink
        );
        String emailContent = getEmailContent(model);
        helper.setText("<html><body>" + emailContent + "</html></body>", true);
        javaMailSender.send(mimeMessage);
    }

    private String getEmailContent(Map<String, Object> model) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Template template = configuration.getTemplate("activation.ftlh");
        template.process(model, stringWriter);
        System.out.println(stringWriter.getBuffer().toString());
        return stringWriter.getBuffer().toString();
    }
}
