package live.easytrain.application.service.interfaces;

import live.easytrain.application.entity.ContactUs;

public interface ContactUsServiceInterface {

    void sendEmail(ContactUs contactUs);

    void saveContactUs(ContactUs contactUs);
}
