package live.easytrain.application.controller;

import live.easytrain.application.dto.BookingDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndTestController {

@GetMapping("/frontend")
public String showFrontEndTest() {
    return "index";
}

@GetMapping("/timetabletest")
public String showTimetableTest() {
    return "timetable";
}

@GetMapping("/timetabletest2")
public String showTimetableTest2() {
    return "timetable-lookup";
}

@GetMapping("/profiletest")
public String showJourneyTest(Model model) {

    model.addAttribute("booking", new BookingDto());
    return "profile/profile";
}

    @GetMapping("/edit-test")
    public String showEditprofileTest() {


        return "profile/profile-edit";
    }

    @GetMapping("/create-test")
    public String showCreateProfileTest() {


        return "profile/profile-complete";
    }

}
