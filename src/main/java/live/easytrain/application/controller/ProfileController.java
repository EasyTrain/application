package live.easytrain.application.controller;

import live.easytrain.application.entity.User;
import live.easytrain.application.repository.UserRepository;
import live.easytrain.application.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalName = authentication.getName();

        User user = userService.getUserByEmail(principalName);
        System.out.println(user);

        model.addAttribute("user", user);

        return "profile/profile";
    }
}
