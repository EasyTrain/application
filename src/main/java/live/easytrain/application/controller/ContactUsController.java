package live.easytrain.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactUsController {

    @GetMapping("/contact-us")
    public String showContactUsForm() {

        return "contact-us";
    }
}
