package live.easytrain.application.controller;

import live.easytrain.application.repository.UserRepository;
import live.easytrain.application.service.UserService;
import org.springframework.stereotype.Controller;
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
    public String showProfile() {

        return "profile/profile";
    }
}
