package live.easytrain.application.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import live.easytrain.application.dto.UserRegistrationDto;
import live.easytrain.application.entity.ChangePasswordRequest;
import live.easytrain.application.entity.Email;
import live.easytrain.application.entity.User;
import live.easytrain.application.repository.UserRepository;
import live.easytrain.application.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration_form";
    }

    @PostMapping("/process_registration")
    public String processRegistration(@Valid @ModelAttribute("user") UserRegistrationDto userRegistrationDto, BindingResult bindingResult,
                                      HttpServletRequest request
            ) {

        if (bindingResult.hasErrors()) {
            return "registration_form";
        }

        try {
            userService.register(userRegistrationDto, getSiteURL(request));

        } catch (UnsupportedEncodingException | MessagingException e) {
            return "registration_form";
        }

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

    @GetMapping("/forgot_password_email_form")
    public String displayEmailForm(Model model) {
        Email email = new Email();
        model.addAttribute("email", email);
        return "forgot-password-email-form";
    }

    @PostMapping("/forgot_password_confirm_email")
    public String submitEmail(@Valid @ModelAttribute("email") Email email, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "forgot-password-email-form";
        }

        userService.submitEmail(email.getEmailAddress(), getSiteURL(request));
        return "forgot-password-email-submitted";
    }

    @GetMapping("/reset_password")
    public String verifyResetPassword(@Param("code") String code, Model model) {
        if (userService.verify(code)) {
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
            Email email = new Email();
            model.addAttribute("changePasswordRequest", changePasswordRequest);
            model.addAttribute("email", email);

            return "reset_password_form";
        } else {
            return "reset_password_fail";
        }
    }

    @PostMapping("/reset_password")
    public String resetPassword(@ModelAttribute("email") Email email,
                                @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest) {

        boolean isPasswordReset = userService.resetPassword(email, changePasswordRequest);

        if (isPasswordReset) {
            return "password_change_success";
        } else {
            return "password_change_failure";
        }
    }
}