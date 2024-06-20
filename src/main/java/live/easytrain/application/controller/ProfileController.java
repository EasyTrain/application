package live.easytrain.application.controller;

import jakarta.validation.Valid;
import live.easytrain.application.entity.User;
import live.easytrain.application.repository.UserRepository;
import live.easytrain.application.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private UserService userService;

    public ProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        model.addAttribute("user", getUser());

        return "profile/profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(Model model) {
        User user = getUser();

        model.addAttribute("user", user);

        return "profile/profile-edit";
    }

    @PostMapping("profile/edit")
    public String editProfile(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "profile/profile-edit";
        }

        userService.saveUser(user);
        return "redirect:/profile";
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalName = authentication.getName();

        return userService.getUserByEmail(principalName);
    }

}
