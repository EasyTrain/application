package live.easytrain.application.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import live.easytrain.application.dto.UserRegistrationDto;
import live.easytrain.application.entity.ChangePasswordRequest;
import live.easytrain.application.entity.Email;
import live.easytrain.application.entity.User;
import live.easytrain.application.exceptions.IncorrectPasswordException;
import live.easytrain.application.exceptions.PasswordsDontMatchException;
import live.easytrain.application.repository.UserRepository;
import live.easytrain.application.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/register/form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register/register_form";
    }

    @GetMapping("/register/success")
    public String showRegistrationSuccess() {
        return "register/register_success";
    }

    @PostMapping("/register/process")
    public String processRegistration(@Valid @ModelAttribute("user") UserRegistrationDto userRegistrationDto, BindingResult bindingResult,
                                      HttpServletRequest request
    ) {

        if (bindingResult.hasErrors()) {
            return "register/register_form";
        }

        try {
            userService.register(userRegistrationDto, getSiteURL(request));

        } catch (UnsupportedEncodingException | MessagingException e) {
            return "register/register_form";
        } catch (IllegalStateException e) {
            bindingResult.addError(new FieldError("duplicateUser", "email", userRegistrationDto.email() + " already exists."));
            return "register/register_form";
        }

        return "redirect:/register/success";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userService.verify(code)) {
            return "verify/verify_success";
        } else {
            return "verify/verify_fail";
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

    @GetMapping("/logout-page")
    public String displayLogout() {
        return "logout";
    }

    @GetMapping("/forgot/email_form")
    public String displayEmailForm(Model model) {
        Email email = new Email();
        model.addAttribute("email", email);
        return "forgot/forgot-email-form";
    }

    @GetMapping("/forgot/email_submitted")
    public String showEmailSubmitted() {
        return "forgot/forgot-email-submitted";
    }

    @PostMapping("/forgot/confirm_email")
    public String submitEmail(@Valid @ModelAttribute("email") Email email, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "forgot/forgot-email-form";
        }

        userService.submitEmail(email.getEmailAddress(), getSiteURL(request));
        return "redirect:/forgot/email_submitted";
    }

    @GetMapping("/forgot/reset_password")
    public String verifyResetPassword(@Param("code") String code, Model model) {
        if (userService.verify(code)) {
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();

            // required to bypass validation on reset_password_form.html
            changePasswordRequest.setPassword("********");

            Email email = new Email();
            model.addAttribute("changePasswordRequest", changePasswordRequest);
            model.addAttribute("email", email);

            return "forgot/reset_password_form";
        } else {
            return "forgot/reset_password_fail";
        }
    }

    @PostMapping("/forgot/reset_password")
    public String resetPassword(@Valid @ModelAttribute("email") Email email, BindingResult bindingResultEmail,
                                @Valid @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest, BindingResult bindingResultChangePasswordRequest) {

        if (bindingResultEmail.hasErrors() || bindingResultChangePasswordRequest.hasErrors()) {
            return "forgot/reset_password_form";
        }

        boolean isPasswordReset = userService.resetPassword(email, changePasswordRequest);

        if (isPasswordReset) {
            return "redirect:/forgot/change_success";
        } else {
            return "redirect:/forgot/change_failure";
        }
    }

    @GetMapping("/forgot/change_success")
    public String displayChangeSuccess() {
        return "forgot/password_change_success";
    }

    @GetMapping("/forgot/change_failure")
    public String displayChangeFailure() {
        return "forgot/password_change_failure";
    }

    @GetMapping("/change_password")
    public String displayChangePasswordForm(Model model) {

        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());

        return "change_password/change_password_form";
    }

    @PostMapping("/change_password")
    public String changePassword(Principal principal, @Valid @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "change_password/change_password_form";
        }

        try {
            userService.changePassword(principal, changePasswordRequest, getSiteURL(request));

        } catch (IncorrectPasswordException e) {
            bindingResult.addError(new FieldError("incorrectPassword", "password", e.getMessage()));
            return "change_password/change_password_form";
        } catch (PasswordsDontMatchException e) {
            bindingResult.addError(new FieldError("passwordsDontMatch", "newPassword", ""));
            bindingResult.addError(new FieldError("passwordsDontMatch", "confirmationPassword", e.getMessage()));
            return "change_password/change_password_form";
        } catch (Exception e) {
            return "register/register_form";
        }

        return "change_password/password_change_success";
    }
}