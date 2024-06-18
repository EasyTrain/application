package live.easytrain.application.service.implentation;

import jakarta.mail.MessagingException;
import live.easytrain.application.entity.ContactUs;
import live.easytrain.application.repository.ContactUsRepository;
import live.easytrain.application.service.interfaces.ContactUsServiceInterface;
import live.easytrain.application.utils.EasyTrainMailSenderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ContactUsService implements ContactUsServiceInterface {

    private ContactUsRepository contactUsRepository;
    private EasyTrainMailSenderUtils mailSenderUtils;

    @Autowired
    public ContactUsService(ContactUsRepository contactUsRepository, EasyTrainMailSenderUtils mailSenderUtils) {
        this.contactUsRepository = contactUsRepository;
        this.mailSenderUtils = mailSenderUtils;
    }

    @Override
    public void sendEmail(ContactUs contactUs) {

        String toAddress = "easyice2024@gmail.com";
        String fromAddress = contactUs.getEmail();

        String senderName = contactUs.getFirstName() + " " + contactUs.getLastName();
        String subject = "[Form] " + contactUs.getSubject();
        String content = "<strong>Name:</strong> " + contactUs.getFirstName() + " " + contactUs.getLastName()
                + "<br><strong>Email:</strong> " + contactUs.getEmail()
                + "<br><strong>Phone Number:</strong> " + contactUs.getPhone()
                + "<br><strong>Subject:</strong> " + contactUs.getSubject()
                + "<br><strong>Message</strong><br>" + contactUs.getContent();

        try {
            mailSenderUtils.sendEmail(fromAddress, toAddress, subject, content, senderName);
            saveContactUs(contactUs);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Something went wrong, email not sent. Please make sure that this email is valid");
        }
    }

    @Override
    public void saveContactUs(ContactUs contactUs) {
        contactUsRepository.save(contactUs);
    }
}
