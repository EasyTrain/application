package live.easytrain.application.controller;

import live.easytrain.application.service.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CommonDataController {

    private final UserService userService;
    private ProfileController profileController;

    public CommonDataController(UserService userService, ProfileController profileController) {
        this.userService = userService;
        this.profileController = profileController;
    }

    @ModelAttribute("username")
    public String getTest() {
        String firstName = profileController.getUser().getFirstName();
        String lastName =  profileController.getUser().getLastName();

        return firstName + " " + lastName;
    }
}
