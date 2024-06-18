package live.easytrain.application.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

@Configuration
public class EasyTrainMailSenderUtils {

    private JavaMailSender mailSender;
    private DateTimeParserUtils dateTimeParser;

    @Autowired
    public EasyTrainMailSenderUtils(JavaMailSender mailSender, DateTimeParserUtils dateTimeParser) {
        this.mailSender = mailSender;
        this.dateTimeParser = dateTimeParser;
    }

    public void sendEmail(String fromAddress, String toAddress, String subject, String content, String senderName)
            throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

}
