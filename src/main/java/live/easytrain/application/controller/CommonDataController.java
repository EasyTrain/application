package live.easytrain.application.controller;

import live.easytrain.application.entity.User;
import live.easytrain.application.service.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice("live.easytrain.application.controller")
public class CommonDataController {

    private final UserService userService;

    public CommonDataController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("attributeName")
    public String getAttributeName(Principal principal) {

        if (principal != null) {
            User user = userService.getUserByEmail(principal.getName());

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            return firstName + " " + lastName;

        }
        return null;
    }
}
