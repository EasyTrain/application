package live.easytrain.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {

    @GetMapping("/about-us")
    public String showAboutUsPage() {

        return "contactUs/about-us";
    }
}
