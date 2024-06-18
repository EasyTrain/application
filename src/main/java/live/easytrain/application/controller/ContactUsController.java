package live.easytrain.application.controller;

import jakarta.validation.Valid;
import live.easytrain.application.entity.ContactUs;
import live.easytrain.application.service.interfaces.ContactUsServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact-us")
public class ContactUsController {

    private ContactUsServiceInterface contactUsService;

    @Autowired
    public ContactUsController(ContactUsServiceInterface contactUsService) {
        this.contactUsService = contactUsService;
    }

    @GetMapping("/form")
    public String contactUsForm(Model model) {

        model.addAttribute("contactUs", new ContactUs());

        return "contactUs/contact-us";
    }

    @PostMapping("/send-form")
    public String contactUsSendForm(@Valid @ModelAttribute("contactUs") ContactUs contactUs,
                                    BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println(contactUs.getEmail());
            return "contactUs/contact-us";
        } else {

            String terms = "You accept our privacy policy.";
            contactUs.setTerms(terms);

            try {
                contactUsService.sendEmail(contactUs);
            } catch (RuntimeException e) {
                model.addAttribute("contactUsError", e.getMessage());
                return "contactUs/contact-us_failed";
            }
        }

        return "contactUs/contact-us_confirm";
    }
}
