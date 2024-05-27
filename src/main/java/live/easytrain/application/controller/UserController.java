package live.easytrain.application.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import live.easytrain.application.entity.User;
import live.easytrain.application.repository.UserRepository;
import live.easytrain.application.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/")
    public String displayHome() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration_form";
    }

    @PostMapping("/process_registration")
    public String processRegistration(User user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        userService.register(user, getSiteURL(request));

        return "register_success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/login")
    public String displayLogin() {
        return "login";
    }

}